package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pandeyDanceAcademy.pda_backend.constants.Constanst_STRs;
import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.repository.EmailRepo;
import pandeyDanceAcademy.pda_backend.service.implementation.EmailSendingService;
import pandeyDanceAcademy.pda_backend.utlity.Generator;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

	@Autowired
	private EmailSendingService emailService;
	@Autowired
	private EmailRepo eamilRepo;
	@Autowired
	EmailDetails eDetails;

	@GetMapping("/loginReq")
	@Transactional
	public void loignRequest(@RequestParam(name = "mailID", required = true) String receiver) {
		System.out.println(receiver + eDetails);

		String otpString = Generator.generateNumberedPassword(5);
		System.out.println(otpString);

		String subject = "Holla Loop Login OPT " + otpString;

		StringBuilder body = new StringBuilder();
		body.append("<html><body><h1>This is an HTML email</h1><p>This is your loging OTP:<br/> <b>" + otpString
				+ "</b></p></body></html>");
//		body.append("This is your loging OTP:<br/> <h2>" + otpString + "</h2>");

		eDetails.setRecipient(receiver);
		eDetails.setSubject(subject);
		eDetails.setMsgBody(body.toString());
		System.out.println(emailService.sendSimpleMailWithHTMLContent(eDetails));

//		eDetails.setRecipient(mailID);

//		eamilRepo.save(null);
	}

	@PostMapping("/sendMail")
	public ResponseEntity<String> sendMail(@Valid @RequestBody EmailDetails details) {
		if (emailService.sendSimpleMail(details)) {
			// Now persist to DB
			return new ResponseEntity<String>(Constanst_STRs.Success, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(Constanst_STRs.Error, HttpStatus.OK);
		}
	}

	@PostMapping("/sendMailWithAttachment")
	public ResponseEntity<String> sendMailWithAttachment(@RequestBody EmailDetails details) {
		if (emailService.sendMailWithAttachment(details)) {
			// Now persist to DB
			return new ResponseEntity<String>(Constanst_STRs.Success, HttpStatus.OK);
		} else {

			return new ResponseEntity<String>(Constanst_STRs.Error, HttpStatus.OK);
		}
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
