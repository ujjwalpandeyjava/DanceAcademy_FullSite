package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
			System.out.println("customer e");
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

	@GetMapping("/all")
	public List<CustomerQueryEntity> getQuery() {
		return customerQueryRepo.findAll();
	}

	// working on this...
	@GetMapping
	public Page<CustomerQueryEntity> getQueryPaginated(@Valid @RequestBody(required = false) QueryPage queryPage) {
		System.out.println(queryPage);
		Order order = queryPage.getSort() == 0 ? Order.desc(queryPage.getSortByKey())
				: Order.asc(queryPage.getSortByKey());
		return customerQueryRepo.findAll(PageRequest.of(0, 3, Sort.by(order)));
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, IOException.class,
			IllegalArgumentException.class, Exception.class })
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
			var ex1 = (IOException) ex;
			errors.put("Message", "IO: " + ex1.getMessage());
		} else if (ex instanceof HttpMessageNotReadableException) {
			var ex1 = (HttpMessageNotReadableException) ex;
			errors.put("Message", "HttpMessageNotReadableException " + ex1.getMessage());
		} else if (ex instanceof IllegalArgumentException) {
			var ex1 = (IllegalArgumentException) ex;
			errors.put("Message", "IllegalArgumentException: " + ex1.getMessage());
		} else {
			var ex1 = (Exception) ex;
			errors.put("Message: ", "Exception: " + ex1.getMessage());
		}
		return errors;
	}

}

class QueryPage {
	@Min(value = 0, message = "Value cannot be lower than 0")
	private int pageNo;
	@Min(value = 1, message = "Value cannot be lower than 1")
	@Max(value = 100, message = "Value cannot excced 100")
	private int pageSize;
	@Min(value = 0, message = "Value cannot be lower than 0")
	@Max(value = 1, message = "Value cannot excced 1")
	private int sort; // 0 asc, 1 desc
	private String sortByKey = "id";

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getSortByKey() {
		return sortByKey;
	}

	public void setSortByKey(String sortByKey) {
		this.sortByKey = sortByKey;
	}

	@Override
	public String toString() {
		return "QueryPage [pageNo=" + pageNo + ", pageSize=" + pageSize + ", sort=" + sort + ", sortByKey=" + sortByKey
				+ "]";
	}

}