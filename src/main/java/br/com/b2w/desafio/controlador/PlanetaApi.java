package br.com.b2w.desafio.controlador;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.b2w.desafio.modelo.Planeta;
import br.com.b2w.desafio.modelo.PlanetaDTO;
import br.com.b2w.desafio.servico.ServicoPlaneta;

@RestController
@RequestMapping("/api/planetas")
public class PlanetaApi {

	@Autowired
	private ServicoPlaneta servicoPlaneta;

	@GetMapping
	public List<Planeta> listarPlanetas(@RequestParam(defaultValue="") String busca) {
		return (busca.isBlank()) ? servicoPlaneta.todos() : servicoPlaneta.buscaPorNome(busca);
	}

	@GetMapping("/{id}")
	public Planeta planeta(@PathVariable BigInteger id) {
		return servicoPlaneta.buscaPorId(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> remover(@PathVariable BigInteger id) {
		servicoPlaneta.deletar(id);
		return ResponseEntity.ok("Planeta removido com sucesso");
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> criar(@RequestBody @Valid PlanetaDTO planeta) {
		BigInteger id = servicoPlaneta.criar(planeta);
		return ResponseEntity.ok("Planeta criado com identificador: " + id);
	}
	

}
