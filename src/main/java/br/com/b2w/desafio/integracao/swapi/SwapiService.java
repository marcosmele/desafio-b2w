package br.com.b2w.desafio.integracao.swapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * API do SWAPI.CO
 * @author marcos
 *
 */
@Service
public class SwapiService {

	@Value("${swapi.url}")
	private String swapiUrl;

	/**
	 * Consulta a lista de planetas do SWAPI.CO filtradas pelo nome.
	 * @param Nome do planeta
	 * @return Lista de planetas e aparições em filmes
	 */
	public List<PlanetaSwapi> obterPlanetas(String nomePlaneta) throws IntegracaoSwapiException{

		String url = swapiUrl + "?search=" + nomePlaneta;

		HttpHeaders cabecalho = new HttpHeaders();

		cabecalho.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		cabecalho.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		RestTemplate swapiApi = new RestTemplate();
		HttpEntity<String> entidade = new HttpEntity<String>("parameters", cabecalho);
		ResponseEntity<ResultSwapi> result = swapiApi.exchange(url, HttpMethod.GET, entidade, ResultSwapi.class);

		try {
			return result.getBody().getResults();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntegracaoSwapiException();
		}
	}

}
