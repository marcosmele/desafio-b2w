package br.com.b2w.desafio;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.b2w.desafio.controlador.PlanetaApi;
import br.com.b2w.desafio.modelo.Planeta;
import br.com.b2w.desafio.modelo.PlanetaDTO;
import br.com.b2w.desafio.servico.ServicoPlaneta;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetaApi.class)
public class PlanetaApiTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ServicoPlaneta servico;

	@Test
	public void listarTodosTest() throws Exception {
		Planeta planeta = new Planeta("Tatooine", "Árido", "Deserto");
		BDDMockito.given(servico.todos()).willReturn(Arrays.asList(planeta));

		ObjectMapper mapper = new ObjectMapper();
		String retornoJson = mapper.writeValueAsString(Arrays.asList(planeta));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/planetas/");
		ResultMatcher retorno = MockMvcResultMatchers.content().json(retornoJson);
		mvc.perform(request).andExpect(retorno);
	}

	@Test
	public void criarTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO("Tatooine", "Árido", "Deserto");

		BDDMockito.given(servico.criar(planeta)).willReturn(new BigInteger("1"));

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(planeta);
		
		String retornoJson = "Planeta criado com identificador: 1";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/planetas/").content(inputJson).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ResultMatcher retorno = MockMvcResultMatchers.content().string(retornoJson);
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void criarSemNomeTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO(null, "Árido", "Deserto");

		BDDMockito.given(servico.criar(planeta)).willReturn(new BigInteger("1"));

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(planeta);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/planetas/").content(inputJson).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void criarSemNomeEClimaTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO(null, "", "Deserto");

		BDDMockito.given(servico.criar(planeta)).willReturn(new BigInteger("1"));

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(planeta);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/planetas/").content(inputJson).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}

}
