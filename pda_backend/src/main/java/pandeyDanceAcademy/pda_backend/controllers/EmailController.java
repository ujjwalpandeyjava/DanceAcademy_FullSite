package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.global.constants.Const;
import pandeyDanceAcademy.pda_backend.respo.inter.EmailDetailsRepo;
import pandeyDanceAcademy.pda_backend.service.inter.EmailSendingServiceInter;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

	Logger logger = LoggerFactory.getLogger(EmailController.class);
	@Autowired
	private EmailSendingServiceInter emailService;
	@Autowired
	private EmailDetailsRepo emailDetailsRepo;

	@PostMapping("/sendMail")
	public ResponseEntity<Object> sendMail(@Valid @RequestBody EmailDetails eMailDetails) {
		if (emailService.sendSimpleMail(eMailDetails))
			return new ResponseEntity<Object>(Map.of(Const.MESSAGE, Const.SUCCESS, Const.CONTENT, emailDetailsRepo.save(eMailDetails)), HttpStatus.OK);
		else
			return new ResponseEntity<Object>(Map.of(Const.MESSAGE, Const.ERROR), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/sendMailWithAttachment")
	public ResponseEntity<String> sendMailWithAttachment(@RequestBody EmailDetails eMailDetails) {
		if (emailService.sendMailWithAttachment(eMailDetails)) {
			// Now persist to DB
			logger.info("E-Mail sent: {}", eMailDetails);
			return new ResponseEntity<String>(Const.SUCCESS, HttpStatus.OK);
		} else
			return new ResponseEntity<String>(Const.ERROR, HttpStatus.OK);
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
