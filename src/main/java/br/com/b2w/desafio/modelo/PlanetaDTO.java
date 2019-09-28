package br.com.b2w.desafio.modelo;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlanetaDTO {
	
	@JsonProperty
	@NotBlank(message="Por favor, especifique o nome do planeta.")
	private String nome;
	
	@JsonProperty
	@NotBlank(message="Por favor, especifique o clima do planeta.")
	private String clima;
	
	@JsonProperty
	@NotBlank(message="Por favor, especifique o terreno do planeta.")
	private String terreno;
	
	public PlanetaDTO(String nome, String clima, String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}

}
