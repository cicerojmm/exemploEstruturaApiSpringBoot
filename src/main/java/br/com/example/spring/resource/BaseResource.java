package br.com.example.spring.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.example.spring.dto.ErrorDTO;

public abstract class BaseResource<T> {

	@Autowired
	protected MessageSource messageSource;

	protected ResponseEntity<T> responderItemCriado(T object) {
		return ResponseEntity.status(HttpStatus.CREATED).body(object);
	}
	
	protected ResponseEntity<T> responderItemNaoEncontrado() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	protected ResponseEntity<T> responderSucesso() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	protected ResponseEntity<T> responderSucessoComItem(T object) {
		return ResponseEntity.status(HttpStatus.OK).body(object);
	}
	
	protected ResponseEntity<List<T>> responderListaVazia() {
		List<T> listaVazia = new ArrayList<>();
		return ResponseEntity.status(HttpStatus.OK).body(listaVazia);
	}
	
	protected ResponseEntity<List<T>> responderListaDeItens(List<T> itens) {
		return ResponseEntity.status(HttpStatus.OK).body(itens);
	}
	
	@ExceptionHandler
	protected ResponseEntity<?> handleBindException(BindException exception) {
		return ResponseEntity.badRequest().body(convert(exception.getAllErrors()));
	}

	/**
	 * Exception handler for validation errors caused by method
	 * parameters @RequesParam, @PathVariable, @RequestHeader annotated with
	 * javax.validation constraints.
	 */
	@ExceptionHandler
	protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {

		List<ErrorDTO> errors = new ArrayList<>();

		for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
			String value = (violation.getInvalidValue() == null ? null : violation.getInvalidValue().toString());
			errors.add(new ErrorDTO(violation.getPropertyPath().toString(), value, violation.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}

		return ResponseEntity.badRequest().body(errors);
	}

	/**
	 * Exception handler for @RequestBody validation errors.
	 */
	@ExceptionHandler
	protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

		return ResponseEntity.badRequest().body(convert(exception.getBindingResult().getAllErrors()));
	}

	/**
	 * Exception handler for missing required parameters errors.
	 */
	@ExceptionHandler
	protected ResponseEntity<?> handleServletRequestBindingException(ServletRequestBindingException exception) {

		return ResponseEntity.badRequest().body(new ErrorDTO(null, null, exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}

	/**
	 * Exception handler for invalid payload (e.g. json invalid format error).
	 */
	@ExceptionHandler
	protected ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

		return ResponseEntity.badRequest().body(new ErrorDTO(null, null, exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}

	protected List<ErrorDTO> convert(List<ObjectError> objectErrors) {

		List<ErrorDTO> errors = new ArrayList<>();

		for (ObjectError objectError : objectErrors) {
			String message = objectError.getDefaultMessage();
			if (message == null) {
				message = messageSource.getMessage(objectError, null);
			}

			ErrorDTO error = null;
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				String value = (fieldError.getRejectedValue() == null ? null
						: fieldError.getRejectedValue().toString());
				error = new ErrorDTO(fieldError.getField(), value, message, HttpStatus.BAD_REQUEST.value());
			} else {
				error = new ErrorDTO(objectError.getObjectName(), objectError.getCode(),
						objectError.getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
			}

			errors.add(error);
		}

		return errors;
	}

}
