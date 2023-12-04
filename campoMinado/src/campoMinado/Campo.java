package campoMinado;


import java.util.ArrayList;
import java.util.List;
import java.lang.Math;


public class Campo {

	final int linha;
	final int coluna;
	
	boolean marcado= false;
	boolean minado=false;
	boolean aberto= false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<Observador> observador= new ArrayList<>();
	public void registrarObservador(Observador observado) {
		observador.add(observado);
		}
	public void notificarObservador (Evento evento) {
		observador.stream().forEach(c -> c.eventoOcorreu(this, evento));
	}
	
	Campo(int linha, int coluna) {
		
		this.linha= linha;
		this.coluna= coluna; 
	}
	
	boolean adicionarVizinhos (Campo vizinho) {
		boolean linhaDiferente= linha!=vizinho.linha;
		boolean colunaDiferente= coluna!= vizinho.coluna;
		boolean diagonal= linhaDiferente && colunaDiferente;
		
		int deltaLinha= Math.abs(linha - vizinho.linha);
		int deltaColuna= Math.abs(coluna - vizinho.coluna);
		int deltaGeral= deltaLinha+deltaColuna;
		
		if (deltaGeral==1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}
		else
		{
			if (deltaGeral==2 && diagonal) {
				vizinhos.add(vizinho);
				return true;
			}
			else {
				return false;
			}
				
		}
	}
	void reiniciarJogo() {
		aberto=false;
		minado=false;
		marcado=false;
		notificarObservador (Evento.REINICIAR);
		
	}
	
	public boolean abrir () {
		if (!aberto && !marcado) {
			setAberto();
			if (minado) {
				notificarObservador(Evento.EXPLODIR);
				return true;
			} 
			else {
				if (vizinhançaSegura()) {
					vizinhos.forEach(v -> v.abrir());
				}
			}
			return true;
		}
		return false;
	}
	public boolean marcar() {
		if (!aberto) {
			if (!marcado)  {
		    marcado= !marcado;
		    notificarObservador(Evento.MARCAR);
		    return true;
			}
			else {
				marcado= !marcado;
				notificarObservador(Evento.DESMARCAR);
				return true;
			}
		}
		else {
			return false;
		}
	}
	boolean minar () {
		minado=true;
		return true;
	}
	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	boolean isMarcado() {
		return marcado;
	}
	boolean isAberto() {
		return aberto;
	}
	public boolean isMinado() {
		return minado;
	}
	public void setAberto() {
		aberto=true;
		notificarObservador(Evento.ABRIR);
		
	}

	public boolean vizinhançaSegura () {
		return vizinhos.stream().noneMatch(v-> v.minado);
		
	}
	public int bombasAoRedor () {
		return (int) vizinhos.stream().filter(v-> v.minado).count();
	}
	
	boolean objetivoAlcançadoCampo () {
		boolean desvendado= aberto && !minado;
		boolean bloqueado= marcado && minado;
		return desvendado || bloqueado;
	}
	
	
	
	
}
 