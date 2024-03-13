package pandeyDanceAcademy.pda_backend.global;

import java.net.http.HttpHeaders;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

//@RestControllerAdvice
public class CustomGlobalExceptionHandler {
/*
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		System.out.println("in exception handling null pointer exception.");

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
		body.put("errors", errors);

		return ResponseEntity.status(status.value()).body(body);

	}*/
}
