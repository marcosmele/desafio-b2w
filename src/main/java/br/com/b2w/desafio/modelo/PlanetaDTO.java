package br.com.b2w.desafio.modelo;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;

@Data
@ApiModel(description="Objeto para criação de Planetas")
@Generated
public class PlanetaDTO {
	
	@JsonProperty
	@NotBlank(message="Por favor, especifique o nome do planeta.")
	@ApiModelProperty(required=true,example="Tatooine")
	private String nome;
	
	@JsonProperty
	@NotBlank(message="Por favor, especifique o clima do planeta.")
	@ApiModelProperty(required=true,example="Deserto")
	private String clima;
	
	@JsonProperty
	@NotBlank(message="Por favor, especifique o terreno do planeta.")
	@ApiModelProperty(required=true,example="Arenoso")
	private String terreno;
	
	public PlanetaDTO(String nome, String clima, String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}

}
