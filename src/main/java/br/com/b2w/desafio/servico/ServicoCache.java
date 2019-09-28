package br.com.b2w.desafio.servico;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b2w.desafio.integracao.swapi.PlanetaSwapi;
import br.com.b2w.desafio.integracao.swapi.SwapiService;

@Service
public class ServicoCache {
	
	private static Map<String, PlanetaSwapi> cachePlanetas;
	
	@Autowired
	private SwapiService swapiService;
	
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
