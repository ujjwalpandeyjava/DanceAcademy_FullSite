package pandeyDanceAcademy.pda_backend.global.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pandeyDanceAcademy.pda_backend.global.constants.Const;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex) {
		Map<String, Object> errors = new HashMap<>();
		System.out.println("global ex");
		errors.put(Const.MESSAGE, "Request method '" + ex.getMethod() + "' is not supported");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Map<String, Object>> httpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException ex) {
		Map<String, Object> errors = new HashMap<>();
		System.out.println("global ex, HttpMediaTypeNotSupportedException");
		errors.put(Const.MESSAGE, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Map<String, Object>> missingServletRequestParameterException(
			MissingServletRequestParameterException ex) {
		Map<String, Object> errors = new HashMap<>();
		System.out.println("global ex, MissingServletRequestParameterException");
		errors.put(Const.MESSAGE, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put(Const.MESSAGE, "Request Body missing!!");
		errors.put("FullMessage", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
