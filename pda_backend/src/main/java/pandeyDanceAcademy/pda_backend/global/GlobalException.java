package pandeyDanceAcademy.pda_backend.global;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		Map<String, Object> errors = new HashMap<>();
		System.out.println("global ex");
		errors.put("Message", "Request method '" + ex.getMethod() + "' is not supported");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}