package br.com.b2w.desafio.controlador;

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
import br.com.b2w.desafio.excecao.PlanetaNotFoundException;
import br.com.b2w.desafio.modelo.Planeta;
import br.com.b2w.desafio.modelo.PlanetaDTO;
import br.com.b2w.desafio.servico.ServicoPlaneta;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetaApi.class)
public class PlanetaApiTests {

	private static final String API_PLANETAS = "/api/planetas/";

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

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_PLANETAS);
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

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_PLANETAS).content(inputJson).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ResultMatcher retorno = MockMvcResultMatchers.content().string(retornoJson);
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void criarSemNomeTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO(null, "Árido", "Deserto");

		BDDMockito.given(servico.criar(planeta)).willReturn(new BigInteger("1"));

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(planeta);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_PLANETAS).content(inputJson).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void criarSemNomeEClimaTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO(null, "", "Deserto");

		BDDMockito.given(servico.criar(planeta)).willReturn(new BigInteger("1"));

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(planeta);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_PLANETAS).content(inputJson).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void buscarNomeTest() throws Exception {
		String nome = "Tatooine";
		Planeta planeta = new Planeta(nome, "Árido", "Deserto");
		BDDMockito.given(servico.buscaPorNome(nome)).willReturn(Arrays.asList(planeta));

		ObjectMapper mapper = new ObjectMapper();
		String retornoJson = mapper.writeValueAsString(Arrays.asList(planeta));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_PLANETAS).param("busca", nome);
		ResultMatcher retorno = MockMvcResultMatchers.content().json(retornoJson);
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void buscarNomeNaoEncontradoTest() throws Exception {
		String nome = "Tatooine";
		BDDMockito.given(servico.buscaPorNome(nome)).willThrow(new PlanetaNotFoundException(nome));

		String retornoJson = "Planeta " + nome + " não encontrado";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_PLANETAS).param("busca", nome);
		ResultMatcher retorno = MockMvcResultMatchers.content().string(retornoJson);
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void buscarIdTest() throws Exception {
		BigInteger id = new BigInteger("1");
		Planeta planeta = new Planeta("Tatooine", "Árido", "Deserto");
		BDDMockito.given(servico.buscaPorId(id)).willReturn(planeta);

		ObjectMapper mapper = new ObjectMapper();
		String retornoJson = mapper.writeValueAsString(planeta);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_PLANETAS+id);
		ResultMatcher retorno = MockMvcResultMatchers.content().json(retornoJson);
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void buscarIdNaoEncontradoTest() throws Exception {
		BigInteger id = new BigInteger("1");
		BDDMockito.given(servico.buscaPorId(id)).willThrow(new PlanetaNotFoundException(id));

		String retornoJson = "Planeta " + id + " não encontrado";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_PLANETAS+id);
		ResultMatcher retorno = MockMvcResultMatchers.content().string(retornoJson);
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void buscarIdInvalidoTest() throws Exception {
		String id = "Tatooine";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_PLANETAS+id);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void deletarTest() throws Exception {
		BigInteger id = new BigInteger("1");
		BDDMockito.doNothing().when(servico).deletar(id);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API_PLANETAS+id);
		ResultMatcher retorno = MockMvcResultMatchers.status().isOk();
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void deletarIdInvalidoTest() throws Exception {
		String id = "Tatooine";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API_PLANETAS+id);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}
	

}
