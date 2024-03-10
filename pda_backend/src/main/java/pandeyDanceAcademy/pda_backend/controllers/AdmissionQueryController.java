package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import pandeyDanceAcademy.pda_backend.repository.AdimissionQueryRepo;

@RestController
@RequestMapping("/api/v1/admissionQuery")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class AdmissionQueryController {

	@Autowired
	private AdimissionQueryRepo admissionQueryRepo;

	@PostMapping
	public ResponseEntity<Map<String, Object>> saveQuery(
			@Valid @RequestBody(required = true) CustomerQueryEntity body) {
		System.out.println(body);
		return new ResponseEntity<Map<String, Object>>(
				Map.of("newUser", admissionQueryRepo.save(body), Constanst_STRs.Message, Constanst_STRs.Success),
				HttpStatus.OK);
	}

	@GetMapping
	public Page<CustomerQueryEntity> getQueryPaginated(@Valid @RequestBody(required = false) GetPagginate queryPage) {
		Order order = queryPage.getSort() == 0 ? Order.desc(queryPage.getSortByKey())
				: Order.asc(queryPage.getSortByKey());
		return admissionQueryRepo
				.findAll(PageRequest.of(queryPage.getPageNo(), queryPage.getPageSize(), Sort.by(order)));
	}

	@GetMapping("/all")
	public Optional<List<CustomerQueryEntity>> getallQueries() {
		return Optional.of(admissionQueryRepo.findAll());
	}

	@GetMapping("/single")
	public Optional<CustomerQueryEntity> getOneQuery(@Valid @RequestBody(required = true) GetOneBody objDetails) {
		return admissionQueryRepo.findById(objDetails.getId());
	}

	@DeleteMapping
	public Map<String, Object> deleteQuery(@Valid @RequestBody(required = true) DeleteBody deleteBody) {
		if (admissionQueryRepo.existsById(deleteBody.getId())) {
			admissionQueryRepo.deleteById(deleteBody.getId());
			return Map.of(Constanst_STRs.Message, Constanst_STRs.Success);
		} else
			return Map.of(Constanst_STRs.Message, Constanst_STRs.Not_Found);
	}

	@PatchMapping
	public ResponseEntity<Map<String, Object>> updateQueryStatus(@Valid @RequestBody(required = true) UpdateBody body) {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		HttpStatus statusCode = HttpStatus.BAD_REQUEST;
		Optional<CustomerQueryEntity> byId = admissionQueryRepo.findById(body.getId());
		if (byId.isPresent()) {
			CustomerQueryEntity query = byId.get();
			if (QueryStatuses.isValidStatus(body.getStatus())) {
				query.setStatus(body.getStatus());
				admissionQueryRepo.save(query);
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
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(Exception ex) {
		System.err.println(ex.getClass() + " | " + ex.getMessage());

		Map<String, Object> errors = new HashMap<>();
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException ex1 = (MethodArgumentNotValidException) ex;
			ex1.getBindingResult().getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
class UpdateBody {
	@NotBlank
	private String id;

	@NotBlank
	private String status;

}

@Data
class GetPagginate {
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