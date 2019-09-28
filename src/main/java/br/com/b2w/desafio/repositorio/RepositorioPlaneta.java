package br.com.b2w.desafio.repositorio;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.b2w.desafio.modelo.Planeta;

public interface RepositorioPlaneta extends MongoRepository<Planeta, BigInteger>{

}
