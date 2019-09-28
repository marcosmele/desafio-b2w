package br.com.b2w.desafio.excecao;

@SuppressWarnings("serial")
public class PlanetaNotFoundException extends RuntimeException {

	public PlanetaNotFoundException(Object identificador) {
	    super("Planeta " + identificador + " não encontrado");
	  }

}
