package br.com.scandiani.calc.modelo;

import java.util.ArrayList;
import java.util.List;


public class Memoria {

	private static Memoria memoria= new Memoria();
	private  String operador="";
	private String texto= "";
	private String textoMemoria= "";
	private  String texto2= "";
	private boolean ultimaOP= false;
	private boolean resultado= false;
	public static Memoria getMemoria() {
		return memoria;
	}
	public String getTexto() {
		if (texto.equals("")) {
			return "0";
		}
		return texto;
	}
	private Memoria() {
		
	}
	private List<MemoriaObservador> observador= new ArrayList<>();
	
	public void registrarObservador(MemoriaObservador observado) {
		observador.add(observado);
		}
	public void notificarObservador (String texto1, String texto2) {
		observador.stream().forEach(c -> c.eventoOcorreu(texto1, texto2));
	}
	public void processarDado(String texto1) {
		if (texto1=="," && texto.contains(",")) {
			return;
		}
		if (texto1.equals("±")) {
			if (texto.endsWith(",00")) {
				texto= texto.substring(0, texto.length()-3);
			}
			if (texto.contains("-")) {
				texto= texto.substring(1);
				notificarObservador(texto, texto2);
				return;
			}
			else {
				texto= "-"+texto;
				notificarObservador(texto, texto2);
				return;
			}
		}
		if (texto1.equals("AC")) {
			texto="";
			operador="";
			textoMemoria= "";
			texto2= "";
			notificarObservador(texto, texto2);
			return;
		}
		if (texto1=="=") {
			if (texto.endsWith(",00")) {
				texto= texto.substring(0, texto.length()-3);
			}
			texto2+= " " +texto + " =";
			texto= calcularResultado();
			textoMemoria= "";
			if (texto.contains(".")) {
				texto=texto.replace('.', ',');
			}
			notificarObservador(texto, texto2);
			texto2= texto;
			resultado= true;
			return;
		}
		// Se não for digitado um número
		if (texto1=="%" || texto1=="*"|| texto1=="/"|| texto1=="-"|| texto1=="+") {
			// Segunda  operação
			if (texto!="" && textoMemoria!="") {
				texto= calcularResultado();
				operador=texto1;
				if (texto.endsWith(",00")) {
					texto= texto.substring(0, texto.length()-3);
				}
				texto2= texto + " " + operador;
				if (texto.contains(".")) {
					texto=texto.replace('.', ',');
				}
				notificarObservador(texto, texto2);
				textoMemoria=texto;
				ultimaOP=true;
				resultado=false;
			} 
			// Se  for a primeira operação
			else {
			operador=texto1;
			textoMemoria= texto;
			ultimaOP=true;
			resultado=false;
			if (texto.endsWith(",00")) {
				texto= texto.substring(0, texto.length()-3);
			}
			if (texto2.equals("")) {
				texto2+= texto + " " + operador;
			} 
			else {
			texto2= texto + " " + operador;
			}
			}
		}
		else {
			// Digitar um número após operação (Limpa o visor)
			if (operador!="" &&  ultimaOP) {
				texto="";
				texto+=texto1;
				notificarObservador(texto, texto2);
				ultimaOP=false;
				resultado=false;
				
			}
			// Primeiro número digitado
			else  {
				if (resultado) {
					texto=texto1;
					texto2="";
				}
				else  {
					texto+=texto1;
				}
				notificarObservador(texto, texto2);
				ultimaOP=false;
				resultado=false;
			}
			
		}
		
		
	}
	private String calcularResultado() {
		if (texto=="" || textoMemoria=="" || texto=="ERROR" || textoMemoria=="ERROR")  {
			return "ERROR";
		}
		if (texto.contains(",")) {
			texto=texto.replace(',', '.');
		}
		if (textoMemoria.contains(",")) {
			textoMemoria=textoMemoria.replace(',', '.');
		}
		
		if (operador=="*") {
			double resultado= Double.parseDouble(texto) * Double.parseDouble(textoMemoria);	
			return String.format("%.2f", resultado);
			
		}
		else {
			if (operador=="+"){
				double resultado= Double.parseDouble(texto) + Double.parseDouble(textoMemoria);	
				return String.format("%.2f", resultado);
			}
			else {
				if (operador=="-"){
					double resultado= Double.parseDouble(textoMemoria) - Double.parseDouble(texto);	
					return String.format("%.2f", resultado);
				}
				else {
					if (operador=="/") {
						double resultado= Double.parseDouble(textoMemoria) / Double.parseDouble(texto);	
						return String.format("%.2f", resultado);
					}
				}
			}
		}
		return "ERROR";
	}
}
