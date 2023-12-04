package br.com.scandiani.calc.visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.scandiani.calc.modelo.Memoria;
import br.com.scandiani.calc.modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements  MemoriaObservador{

	private final JLabel label;
	private final JLabel newLabel;
			
	public Display() {
	    Memoria.getMemoria().registrarObservador(this);
	    setBackground(new Color(46, 49, 50));

	    
	    JPanel labelPanel = new JPanel();
	    labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
	    labelPanel.setBackground(new Color(46, 49, 50));

	    
	    label = new JLabel(Memoria.getMemoria().getTexto());
	    label.setForeground(Color.WHITE);
	    label.setFont(new Font("courier", Font.PLAIN, 20));

	   
	    newLabel = new JLabel("");
	    newLabel.setForeground(Color.WHITE);
	    newLabel.setFont(new Font("courier", Font.PLAIN, 15));

	    
	    labelPanel.add(newLabel);
	    labelPanel.add(label);

	    
	    setLayout(new BorderLayout());
	    add(labelPanel, BorderLayout.EAST);
	}

	@Override
	public void eventoOcorreu(String texto, String texto2) {
		if (texto.endsWith(",00")) {
			texto= texto.substring(0, texto.length()-3);
		}
		if (texto=="") {
			label.setText("0");
			newLabel.setText("");
		}
		else {
			label.setText(texto);
			newLabel.setText(texto2);
		}
		
		
	}
}
