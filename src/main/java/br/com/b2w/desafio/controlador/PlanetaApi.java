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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/planetas")
public class PlanetaApi {

	@Autowired
	private ServicoPlaneta servicoPlaneta;

	@ApiOperation(value = "Listagem de Planetas", response = Planeta.class, responseContainer = "List" )
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Planeta [busca] não encontrado") })
	@GetMapping
	public List<Planeta> listarPlanetas(
			@RequestParam(defaultValue="") 
			@ApiParam(name="busca", value="Filtro de planetas utilizando nome do planeta") 
			String busca) {
		
		return (busca.isBlank()) ? servicoPlaneta.todos() : servicoPlaneta.buscaPorNome(busca);
	}

	@ApiOperation(value = "Busca de planeta pelo idenfiticador", response = Planeta.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Planeta [id] não encontrado") })
	@GetMapping("/{id}")
	public Planeta planeta(
			@PathVariable 
			@ApiParam(name="id", value="Identificador do planeta. Obtido ao incluir um planeta") 
			BigInteger id) {
		
		return servicoPlaneta.buscaPorId(id);
	}

	@ApiOperation(value = "Remoção do planeta pelo idenfiticador", response = Planeta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Planeta removido com sucesso") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> remover(
			@PathVariable 
			@ApiParam(name="id", value="Identificador do planeta. Obtido ao incluir um planeta") 
			BigInteger id) {
		
		servicoPlaneta.deletar(id);
		return ResponseEntity.ok("Planeta removido com sucesso");
	}
	
	@ApiOperation(value = "Criação do planeta")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Planeta criado com identificador: [id]"),
			@ApiResponse(code = 400, message = "Por favor, especifique o [dado] do planeta")})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> criar(
			@RequestBody @Valid 
			@ApiParam(name="planeta", value="Planeta a ser criado",example="{\"nome\": \"Tatooine\",\"clima\": \"Árido\",\"terreno\": \"Arenoso\"}")
			PlanetaDTO planeta) {
		
		BigInteger id = servicoPlaneta.criar(planeta);
		return ResponseEntity.ok("Planeta criado com identificador: " + id);
	}
	

}
