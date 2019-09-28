package br.com.b2w.desafio.integracao.swapi;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetaSwapi {

	private String name;

	private List<String> films;
	
	@JsonIgnore
	private Date ultimaConsulta;

	public PlanetaSwapi(String name, List<String> films) {
			this.name = name;
			this.films = films;
		}

}
