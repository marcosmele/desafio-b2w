package br.com.b2w.desafio.servico;

import java.math.BigInteger;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import br.com.b2w.desafio.excecao.PlanetaNotFoundException;
import br.com.b2w.desafio.modelo.Planeta;
import br.com.b2w.desafio.modelo.PlanetaDTO;
import br.com.b2w.desafio.repositorio.RepositorioPlaneta;

/**
 * Serviço com operações CRUD sobre planetas
 * @author marcos
 *
 */
@Service
public class ServicoPlaneta {
	
	@Autowired
	private RepositorioPlaneta repositorio;
	
	@Autowired
	private ServicoCache cache;
	
	/**
	 * Lista todos os planetas cadastrados.
	 */
	public List<Planeta> todos(){
		List<Planeta> lista = repositorio.findAll();
		lista.forEach(consumer->{
			consumer.setAparicoes(cache.consulta(consumer.getNome()));
		});
		return lista;
	}
	
	/**
	 * Realiza a busca de planeta a partir do seu identificador. 
	 * @param identificador
	 * @throws PlanetaNotFoundException se não encontrar um planeta
	 * @return
	 */
	public Planeta buscaPorId(BigInteger id){
		Planeta planeta = repositorio.findById(id).orElseThrow(()-> new PlanetaNotFoundException(id));
		planeta.setAparicoes(cache.consulta(planeta.getNome()));
		return planeta;
	}
	
	/**
	 * Remove o planeta da base a partir do seu identificador. 
	 * @param identificador
	 * @return
	 */
	public void deletar(BigInteger id){
		repositorio.deleteById(id);
	}

	/**
	 * Realiza a busca de planeta a partir de uma string. <br>
	 * A busca pode ser feita utilizando REGEX. <br>
	 * @param nome (ou string regex) do planeta a ser buscado
	 * @throws PlanetaNotFoundException se não encontrar um planeta
	 * @return
	 */
	public List<Planeta> buscaPorNome(String nome) {
		Planeta exemplo = new Planeta();
		exemplo.setNome(nome);
		
		ExampleMatcher withStringMatcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.REGEX);
		List<Planeta> lista = repositorio.findAll(Example.of(exemplo, withStringMatcher));
		
		if (lista.isEmpty()) {
			throw new PlanetaNotFoundException(nome);
		}
		
		lista.forEach(consumer->{
			consumer.setAparicoes(cache.consulta(consumer.getNome()));
		});
		return lista;		
	}

	/**
	 * Criação do planeta na base de dados
	 * @param planetaDTO - DTO contendo os dados necessários para inclusão do planeta.
	 * @return identificador do planeta para futuras consultas
	 */
	public BigInteger criar(PlanetaDTO planetaDTO) {
		Planeta planeta = new Planeta();
		new ModelMapper().map(planetaDTO, planeta);
		
		return repositorio.save(planeta).getId();
	}

}
