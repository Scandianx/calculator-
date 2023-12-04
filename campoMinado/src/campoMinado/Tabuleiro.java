package campoMinado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Tabuleiro implements Observador{
    private int linha;
    private int coluna;
    private int minas;
	public List<Campo> campos = new ArrayList<>();
	private final List<Consumer<Boolean>> observadores= new ArrayList<>();
	
	
	
	public Tabuleiro(int linha, int coluna, int minas) {
		this.linha= linha;
		this.coluna=coluna;
		this.minas=minas;
		preencherTabuleiro();
		associarVizinhos();
		sortearMinas();
		
	}
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void registrarObservador(Consumer<Boolean> observador) {
		 observadores.add(observador);
	}
	public void notificarObservador (boolean resultado) {
		observadores.stream().forEach(v -> v.accept(resultado));
	}
	
	public boolean objetivoAlcançado () {
		return campos.stream().allMatch(c-> c.objetivoAlcançadoCampo());
	}
	
	public void  reiniciarTabuleiro () {
		campos.forEach(c -> c.reiniciarJogo());
		sortearMinas();
	}
	
	  private boolean sortearMinas() {
		Random random= new Random();
		while (!((int)(campos.stream().filter(v-> v.minado).count())==minas)) {
			int numeroAleatorio= random.nextInt(linha);
			int numeroAleatorio2= random.nextInt(coluna);
			for (Campo campo: campos) {
				if (campo.getLinha()==numeroAleatorio && campo.getColuna()==numeroAleatorio2) {
					 campo.minar();
				}
			}
		}
		return ((int)(campos.stream().filter(v-> v.minado).count())==minas);
	}

	private void associarVizinhos() {
		for (Campo campo:campos) {
			for (Campo campo2:campos) {
				campo.adicionarVizinhos(campo2);
			}
		}
		
	}

	private void preencherTabuleiro() {
		for(int i=0; i<linha; i++) {
			for (int c=0; c<coluna; c++) {
				Campo campo= new Campo(i, c);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
	}
	public int getLinha() {
		return linha;
	}


	public int getColuna() {
		return coluna;
	}
    void revelarMinas() {
    	campos.stream().filter(v-> v.isMinado()).filter(v-> !v.isMarcado()).forEach(v->v.setAberto());
    }
	
	public void abrirCampo (int linhax, int colunax) {
		campos.parallelStream()
		.filter(v->v.getLinha()==linhax && v.getColuna()==colunax)
		.findFirst().ifPresent(c->c.abrir());
	}
	public void marcarCampo (int linhax, int colunax) {
		campos.parallelStream()
		.filter(v->v.getLinha()==linhax && v.getColuna()==colunax)
		.findFirst().ifPresent(c->c.marcar());
	}
	
	
	

	@Override
	public void eventoOcorreu(Campo campo, Evento evento) {
		if (evento == Evento.EXPLODIR) {
			System.out.println("Você perdeu");
			revelarMinas();
			notificarObservador (false);
		}
		else {
			if (objetivoAlcançado()) {
				System.out.println("Você ganhou");
				notificarObservador (true);
			}
		}
		
	}
	
	
	
	
}
