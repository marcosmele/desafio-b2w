package br.com.b2w.desafio.servico;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.b2w.desafio.integracao.swapi.IntegracaoSwapiException;
import br.com.b2w.desafio.integracao.swapi.PlanetaSwapi;
import br.com.b2w.desafio.integracao.swapi.SwapiService;

@RunWith(SpringRunner.class)
@WebMvcTest(ServicoCache.class)
public class ServicoCacheTests {
	
	@Autowired
	private ServicoCache servico;
	
	@MockBean
	private SwapiService swapi;

	
	@Test
	public void consultaFilmesErroIntegracaoTest() throws IntegracaoSwapiException {
		String nome = "Dagobah";
		
		BDDMockito.given(swapi.obterPlanetas(nome)).willThrow(IntegracaoSwapiException.class);
		
		assertEquals(0,servico.consulta(nome));
	}
	
	@Test
	public void consultaFilmesTest() throws IntegracaoSwapiException {
		String nome = "Tatooine";
		PlanetaSwapi planetaMock = new PlanetaSwapi(nome, Arrays.asList("1","2","3"));
		
		BDDMockito.given(swapi.obterPlanetas(nome)).willReturn(Arrays.asList(planetaMock));
		
		assertEquals(3,servico.consulta(nome));
	}
	
	@Test
	public void consultaFilmesCacheTest() throws IntegracaoSwapiException {
		String nome = "Endor";
		PlanetaSwapi planetaMock = new PlanetaSwapi(nome, Arrays.asList("1","2","3"));
		
		BDDMockito.given(swapi.obterPlanetas(nome)).willReturn(Arrays.asList(planetaMock));
		
		assertEquals(3,servico.consulta(nome));
		
		BDDMockito.given(swapi.obterPlanetas(nome)).willThrow(IntegracaoSwapiException.class);
		
		assertEquals(3,servico.consulta(nome));
	}

}
