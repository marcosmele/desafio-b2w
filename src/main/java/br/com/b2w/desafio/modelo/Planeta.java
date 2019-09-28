package br.com.b2w.desafio.modelo;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
public class Planeta {
	
	@Id
	private BigInteger id;
	
	@Indexed
	private String nome;
	
	private String clima;
	
	private String terreno;
	
	@Transient
	private int aparicoes;
	
	public Planeta(String nome, String clima, String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}
}
