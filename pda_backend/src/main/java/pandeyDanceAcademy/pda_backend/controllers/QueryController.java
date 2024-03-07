package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pandeyDanceAcademy.pda_backend.constants.Constanst_STRs;
import pandeyDanceAcademy.pda_backend.enums.RESPONSE_STATUS;
import pandeyDanceAcademy.pda_backend.model.CustomerQueryEntity;
import pandeyDanceAcademy.pda_backend.repository.CustomerQueryRepo;

@RestController
@RequestMapping("/api/v1/query")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class QueryController {

	@Autowired
	private CustomerQueryRepo customerQueryRepo;

	@PostMapping(value = "/add")
	public ResponseEntity<Map<String, Object>> saveQuery(
			@Valid @RequestBody(required = false) CustomerQueryEntity customerQueryEntity) {
		JSONObject result = new JSONObject();

		if (customerQueryEntity == null) {
			result.put(Constanst_STRs.Message, RESPONSE_STATUS.EMPTY_BODY.getVal());
			result.put(Constanst_STRs.Action, "Request body is missing");
			return new ResponseEntity<Map<String, Object>>(result.toMap(), HttpStatus.BAD_GATEWAY);
		}

		System.out.println(customerQueryEntity);

		result.put("newUser", customerQueryRepo.save(customerQueryEntity));
		return new ResponseEntity<Map<String, Object>>(result.toMap(), HttpStatus.OK);
	}

	@PostMapping(value = "/add_")
	public ResponseEntity<Map<String, Object>> saveQuery_(
			@Valid @RequestBody(required = true) CustomerQueryEntity customerQueryEntity) {
		JSONObject result = new JSONObject();
		result.put("newUser", customerQueryRepo.save(customerQueryEntity));
		return new ResponseEntity<Map<String, Object>>(result.toMap(), HttpStatus.OK);
	}

	@GetMapping
	public List<CustomerQueryEntity> getQuery() {
		return customerQueryRepo.findAll();
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class,
			IOException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationExceptions(Exception ex) {
		Map<String, String> errors = new HashMap<>();
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException ex1 = (MethodArgumentNotValidException) ex;
			ex1.getBindingResult().getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
		} else if (ex instanceof IOException) {
			IOException ex1 = (IOException) ex;
			errors.put("Message", "IO e : " + ex1.getMessage());
		} else if (ex instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException ex1 = (HttpMessageNotReadableException) ex;
			errors.put("Message", "HttpMessageNotReadableException e : " + ex1.getMessage());
		}
		return errors;
	}
}
