package br.com.b2w.desafio.modelo;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@ApiModel(description="Objeto para exibição de Planetas")
@Generated
public class Planeta {
	
	@Id
	@JsonFormat(shape=JsonFormat.Shape.STRING)
	@ApiModelProperty(value="Identificador único do Planeta, utilizado para buscas imediatas")
	private BigInteger id;
	
	@Indexed
	@ApiModelProperty(value="Nome do Planeta, utilizado para buscas")
	private String nome;
	
	@ApiModelProperty(value="Clima do Planeta")
	private String clima;
	
	@ApiModelProperty(value="Terreno do Planeta")
	private String terreno;
	
	@Transient
	@ApiModelProperty(value="Quantidade de aparições do planeta nos filmes")
	private int aparicoes;
	
	public Planeta(String nome, String clima, String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}
}
