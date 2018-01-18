package ro.ubb.cs.re.jcm.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.ubb.cs.re.jcm.exception.CandidateNotFoundException;
import ro.ubb.cs.re.jcm.exception.SpecializationNotFoundException;

@RestControllerAdvice
public class ErrorAdvice {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({CandidateNotFoundException.class, SpecializationNotFoundException.class})
	public ErrorEntity handleNotFound(RuntimeException e) {
		return new ErrorEntity(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ErrorEntity handleValidation(MethodArgumentNotValidException e) {
		return new ErrorEntity(e.getBindingResult().getFieldError().getDefaultMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public ErrorEntity handleAll(Exception e) {
		return new ErrorEntity(e.getMessage());
	}

	@Data
	private static class ErrorEntity {
		private final String error;
	}
}
