package visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import campoMinado.Campo;
import campoMinado.Evento;
import campoMinado.Observador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements Observador, MouseListener {
	private Campo campo;
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);
	BotaoCampo (Campo campo) {
		this.campo= campo;
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		campo.registrarObservador(this);
		addMouseListener(this);
	}

	@Override
	public void eventoOcorreu(Campo campo, Evento evento) {
		switch (evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default: 
			aplicarEstiloPadrao();
		}
		
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setText("M");
		
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setText("X");
		
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setText("");
		setBorder(BorderFactory.createBevelBorder(0));
		
	}

	private void aplicarEstiloAbrir() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if (campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		setBackground(BG_PADRAO);
		if (!campo.isMinado()) {
		switch (campo.bombasAoRedor()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.PINK);
			break;
		default:
			setForeground(Color.RED);
			
		}
		String valor= !campo.vizinhançaSegura() ? campo.bombasAoRedor() + "" : "";
		setText(valor);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	   if(e.getButton() == 1) {
		   campo.abrir();
	   }
	   else {
		   campo.marcar();
	   }
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
