package br.com.b2w.desafio.repositorio;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.b2w.desafio.modelo.Planeta;

/**
 * Repositorio utilizado para manipulação dos Planetas na base de dados.
 * @author marcos
 *
 */
public interface RepositorioPlaneta extends MongoRepository<Planeta, BigInteger>{

}
