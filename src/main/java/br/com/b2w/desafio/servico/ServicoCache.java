package br.com.b2w.desafio.servico;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b2w.desafio.integracao.swapi.PlanetaSwapi;
import br.com.b2w.desafio.integracao.swapi.SwapiService;

/**
 * Serviço que mantem um cache (renovado após uma semana) das aparições dos planetas em filmes, utilizando a API pública SWAPI.CO.<br>
 * O cache é montado a partir de cada consulta realizada.
 * @author marcos
 *
 */
@Service
public class ServicoCache {
	
	private static Map<String, PlanetaSwapi> cachePlanetas = new HashMap<String, PlanetaSwapi>();
	
	@Autowired
	private SwapiService swapiService;
	
	/**
	 * Consulta o total de aparicoes em filme de um planeta utilizando a API pública SWAPI.CO.<br>
	 * Caso possua o planeta em cache, retorna o total de aparições instantaneamente.<br>
	 * Para nao ser dependente da API, retorna 0 caso encontre um problema
	 * @param Nome do Planeta
	 * @return Total de aparições
	 */
	public int consulta(String nomePlaneta) {
		Date agora = new Date();
		try {
			PlanetaSwapi cachePlaneta = cachePlanetas.get(nomePlaneta);
			if(cachePlaneta == null || cachePlaneta.getUltimaConsulta().before(ultimaSemana())) {
				List<PlanetaSwapi> planetasSwapi = swapiService.obterPlanetas(nomePlaneta);
				
				PlanetaSwapi planetaSwapi = planetasSwapi
						.stream()
						.filter(p->p.getName().equals(nomePlaneta))
						.findFirst()
						.get();
				
				planetaSwapi.setUltimaConsulta(agora);
				cachePlanetas.put(planetaSwapi.getName(), planetaSwapi);
				
				return planetaSwapi.getFilms().size();
				
			} else {
				return cachePlaneta.getFilms().size();
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	private Date ultimaSemana(){
	     return new Date(System.currentTimeMillis()-7*24*60*60*1000);
	}

}
