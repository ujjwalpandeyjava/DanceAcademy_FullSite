package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pandeyDanceAcademy.pda_backend.constants.Constanst_STRs;
import pandeyDanceAcademy.pda_backend.constants.QueryStatuses;
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
			@Valid @RequestBody(required = true) CustomerQueryEntity customerQueryEntity) {
		JSONObject result = new JSONObject();
		result.put("newUser", customerQueryRepo.save(customerQueryEntity));
		return new ResponseEntity<Map<String, Object>>(result.toMap(), HttpStatus.OK);
	}

	@GetMapping
	public Page<CustomerQueryEntity> getQueryPaginated(@Valid @RequestBody(required = false) QueryPage queryPage) {
		Order order = queryPage.getSort() == 0 ? Order.desc(queryPage.getSortByKey())
				: Order.asc(queryPage.getSortByKey());
		return customerQueryRepo
				.findAll(PageRequest.of(queryPage.getPageNo(), queryPage.getPageSize(), Sort.by(order)));
	}

	@GetMapping("/all")
	public Optional<List<CustomerQueryEntity>> getallQueries() {
		return Optional.of(customerQueryRepo.findAll());
	}

	@GetMapping("/single")
	public Optional<CustomerQueryEntity> getOneQuery(@Valid @RequestBody(required = true) GetOneBody objDetails) {
		return customerQueryRepo.findById(objDetails.getId());
	}

	@DeleteMapping("/delete")
	public Map<String, Object> deleteQuery(@Valid @RequestBody(required = true) DeleteBody deleteBody) {
		if (customerQueryRepo.existsById(deleteBody.getId())) {
			customerQueryRepo.deleteById(deleteBody.getId());
			return Map.of(Constanst_STRs.Message, Constanst_STRs.Success);
		} else
			return Map.of(Constanst_STRs.Message, Constanst_STRs.Not_Found);
	}

	@PatchMapping
	public ResponseEntity<Map<String, Object>> updateQueryStatus(
			@Valid @RequestBody(required = true) UpdateQueryBody body) {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		HttpStatus statusCode = HttpStatus.BAD_REQUEST;
		Optional<CustomerQueryEntity> byId = customerQueryRepo.findById(body.getId());
		if (byId.isPresent()) {
			CustomerQueryEntity query = byId.get();
			if (QueryStatuses.isValidStatus(body.getStatus())) {
				query.setStatus(body.getStatus());
				customerQueryRepo.save(query);
				returnObj = Map.of(Constanst_STRs.Message, Constanst_STRs.Success);
				statusCode = HttpStatus.OK;
			} else {
				returnObj = Map.of(Constanst_STRs.Message, Constanst_STRs.NotValidStatus);
				statusCode = HttpStatus.CONFLICT;
			}
		} else {
			returnObj = Map.of(Constanst_STRs.Message, Constanst_STRs.Not_Found);
			statusCode = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<Map<String, Object>>(returnObj, statusCode);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, IOException.class,
			IllegalArgumentException.class, Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)

	public Map<String, String> handleValidationExceptions(Exception ex) {
		System.err.println(ex.getClass() + " | " + ex.getMessage());

		Map<String, String> errors = new HashMap<>();
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException ex1 = (MethodArgumentNotValidException) ex;
			ex1.getBindingResult().getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
		} else if (ex instanceof IOException) {
			var ex1 = (IOException) ex;
			errors.put("Message", ex1.getMessage());
		} else if (ex instanceof HttpMessageNotReadableException) {
			var ex1 = (HttpMessageNotReadableException) ex;
			System.err.println(ex1.getMessage());
			errors.put("Message", "Request Body missing!");
		} else if (ex instanceof IllegalArgumentException) {
			var ex1 = (IllegalArgumentException) ex;
			errors.put("Message", ex1.getMessage());
		} else {
			var ex1 = (Exception) ex;
			errors.put("Message: ", ex1.getMessage());
		}
		return errors;
	}

}

@Data
class DeleteBody {
	@NotNull
	private String id;

}

@Data
class GetOneBody {
	@NotNull
	private String id;

}

@Data
class UpdateQueryBody {
	@NotBlank
	private String id;

	@NotBlank
//	if a number
//	@Pattern(regexp = "^[1-9]|10$", message = "Status must be a number between 1 and 10")
	private String status;

}

@Data
class QueryPage {
	@Min(value = 0, message = "Value cannot be lower than 0")
	private int pageNo;
	@Min(value = 1, message = "Value cannot be lower than 1")
	@Max(value = 100, message = "Value cannot excced 100")
	private int pageSize;
	@Min(value = 0, message = "Value cannot be lower than 0")
	@Max(value = 1, message = "Value cannot excced 1")
	private int sort; // 0 ascending, 1 descending
	private String sortByKey = "id";

}