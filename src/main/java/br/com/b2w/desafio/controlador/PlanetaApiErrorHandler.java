package br.com.b2w.desafio.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.b2w.desafio.excecao.PlanetaNotFoundException;

@ControllerAdvice
class PlanetaApiErrorHandler {

	@ResponseBody
	@ExceptionHandler(PlanetaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String planetNotFoundHandler(PlanetaNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    List<String> methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {

        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return erros;

    }

}
