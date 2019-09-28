package br.com.b2w.desafio.servico;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

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

@Service
public class ServicoPlaneta {
	
	@Autowired
	private RepositorioPlaneta repositorio;
	
	@Autowired
	private ServicoCache cache;
	
	public List<Planeta> todos(){
		//TODO Paginacao
		List<Planeta> lista = repositorio.findAll();
		lista.forEach(consumer->{
			consumer.setAparicoes(cache.consulta(consumer.getNome()));
		});
		return lista;
	}
	
	public Planeta buscaPorId(BigInteger id){
		Planeta planeta = repositorio.findById(id).orElseThrow(()-> new PlanetaNotFoundException(id));
		planeta.setAparicoes(cache.consulta(planeta.getNome()));
		return planeta;
	}
	
	public void deletar(BigInteger id){
		repositorio.deleteById(id);
	}

	public List<Planeta> buscaPorNome(String busca) {
		//TODO Cache dos filmes
		Planeta exemplo = new Planeta();
		exemplo.setNome(busca);
		
		ExampleMatcher withStringMatcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.REGEX);
		List<Planeta> lista = repositorio.findAll(Example.of(exemplo, withStringMatcher));
		
		lista.forEach(consumer->{
			consumer.setAparicoes(cache.consulta(consumer.getNome()));
		});
		return Optional.of(lista).orElseThrow(()->new PlanetaNotFoundException(busca));		
	}

	public BigInteger criar(PlanetaDTO planetaDTO) {
		Planeta planeta = new Planeta();
		new ModelMapper().map(planetaDTO, planeta);
		
		return repositorio.save(planeta).getId();
	}

}
