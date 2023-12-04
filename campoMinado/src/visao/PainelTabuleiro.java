package visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import campoMinado.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		setLayout(new GridLayout(tabuleiro.getLinha(), tabuleiro.getColuna()));
		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
		
		tabuleiro.registrarObservador(e -> { SwingUtilities.invokeLater(() ->{
			if (e) {
			JOptionPane.showMessageDialog(this, "Você ganhou, parabéns!");
		}
		else {
			JOptionPane.showMessageDialog(this, "Você perdeu :(");
		}
	    tabuleiro.reiniciarTabuleiro(); });
			
		});
	}
}
