package br.com.b2w.desafio.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.b2w.desafio.excecao.PlanetaNotFoundException;
import br.com.b2w.desafio.modelo.PlanetaDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles="test")
public class ServicoPlanetaTests {
	
	@Autowired
	private ServicoPlaneta servico;
	
	@Test
	public void criarTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		BigInteger id = servico.criar(planeta);
		
		assertNotNull(id);
	}
	
	@Test
	public void deletarTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		BigInteger id = servico.criar(planeta);
		
		servico.deletar(id);
	}
	
	@Test
	public void listarTodosTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		servico.criar(planeta);
		
		assertEquals(1,servico.todos().size());
	}
	
	@Test
	public void buscarIdTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		BigInteger id = servico.criar(planeta);
		
		assertNotNull(servico.buscaPorId(id));
	}
	
	@Test(expected=PlanetaNotFoundException.class)
	public void buscarIdNaoEncontradoTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		servico.criar(planeta);
		
		servico.buscaPorId(new BigInteger("123"));
	}
	
	@Test
	public void buscarNomeTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		servico.criar(planeta);
		
		assertNotNull(servico.buscaPorNome("Tatooine"));
	}
	
	@Test(expected=PlanetaNotFoundException.class)
	public void buscarNomeNaoEncontradoTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		servico.criar(planeta);
		
		servico.buscaPorNome("BLA");
	}
	
	@Test
	public void buscarNomeRegexTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		servico.criar(planeta);
		
		assertNotNull(servico.buscaPorNome("Tatoo*"));
	}
	
	

}
