package br.com.example.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.example.spring.dto.ErrorDTO;

@ControllerAdvice
public class ControllerAdviceException {

	@ExceptionHandler(value = { RestAPIException.class })
	public ResponseEntity<Object> exception(RestAPIException exception) {
		return new ResponseEntity<>(new ErrorDTO(null, null, exception.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}
}
