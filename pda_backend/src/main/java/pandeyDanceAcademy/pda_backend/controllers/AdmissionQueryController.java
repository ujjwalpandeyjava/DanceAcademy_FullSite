package pandeyDanceAcademy.pda_backend.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.server.PathParam;
import lombok.Builder;
import lombok.Data;
import pandeyDanceAcademy.pda_backend.entity.CustomerQueryEntity;
import pandeyDanceAcademy.pda_backend.entity.CustomerQueryEntity.CustomerQueryEntityBuilder;
import pandeyDanceAcademy.pda_backend.entity.File_Type;
import pandeyDanceAcademy.pda_backend.entity.File_Type.File_TypeBuilder;
import pandeyDanceAcademy.pda_backend.global.constants.Const;
import pandeyDanceAcademy.pda_backend.global.constants.QueryStatuses;
import pandeyDanceAcademy.pda_backend.service.respoInter.AdimissionQueryRepo;

@RestController
@RequestMapping("/api/v1/admissionQuery")
//@MultipartConfig(maxFileSize = 100000, fileSizeThreshold = 1000)
public class AdmissionQueryController {

	private Logger logger = LoggerFactory.getLogger(AdmissionQueryController.class);
	@Autowired
	private AdimissionQueryRepo admissionQueryRepo;

	/**
	 * Save the user admission query, with multiple files with storage, and one file
	 * in binary form.
	 * 
	 * @param body all the parameters in an object.
	 * @return the created object.
	 */
	@PostMapping(value = "/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> saveQuery(@Valid @ModelAttribute CustomerQueryEntityModel body) {
		ArrayList<File_Type> files = new ArrayList<File_Type>();
		if (body.getNImgList() != null) {
			logger.info("len: {}, data: {}", body.getNImgList().length, body.getNImgList().toString());
			Arrays.stream(body.getNImgList()).forEach(multipart -> {
				String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss__"))
						+ multipart.getOriginalFilename();
				String rootDirectory = new File("").getAbsolutePath();
				String filePath = rootDirectory + "/uploads/" + fileName;
				try {
					File uploadsDir = new File(rootDirectory + "/uploads");
					if (!uploadsDir.exists())
						uploadsDir.mkdir();
					File file = new File(filePath);
					multipart.transferTo(file);
					File_TypeBuilder fileDetails = File_Type.builder().originalName(multipart.getOriginalFilename())
							.filePath(filePath).type(multipart.getContentType());
					files.add(fileDetails.build());
				} catch (IOException e) {
					logger.error("Failed to save file: {} ", e.getMessage());
				}
				logger.warn("File paths: {}", filePath);
			});
		}

		CustomerQueryEntityBuilder cqeb = CustomerQueryEntity.builder().name(body.getName()).gender(body.getGender())
				.danceForm(body.getDanceForm()).email(body.getEmail()).contactNo(body.getContactNo())
				.guardianContactNo(body.getGuardianContactNo()).address(body.getAddress())
				.description(body.getDescription()).createdDate(new Date()).status(QueryStatuses.New).files(files);

		if (body.getOneImg() != null) {
			try {
				cqeb.imageBase64(new Binary(body.getOneImg().getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new ResponseEntity<Map<String, Object>>(
				Map.of("newUser", admissionQueryRepo.save(cqeb.build()), Const.MESSAGE, Const.SUCCESS),
				HttpStatus.CREATED);
	}

	@PostMapping("/paginated")
	public Page<CustomerQueryEntity> getQueryPaginated(@Valid @RequestBody GetPagginate pq) {
		Order order = pq.getSort() < 0 ? Order.desc(pq.getSortByKey()) : Order.asc(pq.getSortByKey());
		return admissionQueryRepo.findAllByStatus(PageRequest.of(pq.getPageNo(), pq.getPageSize(), Sort.by(order)),
				pq.getStatus());
	}

	@GetMapping("/all")
	public Optional<List<CustomerQueryEntity>> getallQueries() {
		return Optional.of(admissionQueryRepo.findAll());
	}

	@GetMapping("/:id")
	public Optional<CustomerQueryEntity> getOneQuery(@Valid @PathParam("id") String id) {
		System.out.println("===" + id);
		return admissionQueryRepo.findById(id);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateStatusOneQuery(@PathVariable("id") String id,
			@RequestParam String status) {
		logger.info("status {}", status);

		if (!QueryStatuses.isValidStatus(status))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of(Const.MESSAGE, "Statuses can be only: " + QueryStatuses.getAllStatus()));

		Optional<CustomerQueryEntity> query = admissionQueryRepo.findById(id);
		if (query.isPresent()) {
			CustomerQueryEntity cqe = query.get();
			cqe.setStatus(status);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(
					Map.of(Const.MESSAGE, "Status updated succesfully.", "updatedUser", admissionQueryRepo.save(cqe)));
		} else
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(Map.of(Const.MESSAGE, "Query not found!"));
	}

	@DeleteMapping("/{id}")
	public Map<String, Object> deleteQuery(@PathVariable String id) {
		if (admissionQueryRepo.existsById(id)) {
			admissionQueryRepo.deleteById(id);
			return Map.of(Const.MESSAGE, Const.SUCCESS);
		} else
			return Map.of(Const.MESSAGE, Const.Not_Found);
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
				returnObj = Map.of(Const.MESSAGE, Const.SUCCESS);
				statusCode = HttpStatus.OK;
			} else {
				returnObj = Map.of(Const.MESSAGE, Const.NotValidStatus);
				statusCode = HttpStatus.CONFLICT;
			}
		} else {
			returnObj = Map.of(Const.MESSAGE, Const.Not_Found);
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
	private int sort; // 1 ascending, -1 descending
	private String sortByKey = "createdDate";
	private String status;
}

@Data
@Builder
class CustomerQueryEntityModel {
	@NotBlank
	private String name;
	@NotBlank(message = "Must specify gender")
	private String gender;
	@NotBlank(message = "DanceForm must be filled")
	private String danceForm;
	private String email;
	@NotBlank(message = "Contact must be given for connecting purpose")
	private String contactNo;
	private String guardianContactNo;
	private String address;
	private String description;
	private String createdDate;
	private String status;
	private MultipartFile oneImg;
	private MultipartFile[] nImgList;
}
