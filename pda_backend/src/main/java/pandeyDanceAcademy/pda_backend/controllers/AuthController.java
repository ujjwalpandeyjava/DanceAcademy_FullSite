package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pandeyDanceAcademy.pda_backend.config.JWT.JWTHelper;
import pandeyDanceAcademy.pda_backend.config.JWT.models.LoginReqBody;
import pandeyDanceAcademy.pda_backend.constants.Constant_Num;
import pandeyDanceAcademy.pda_backend.constants.Constant_String;
import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.entity.RegisteredUser;
import pandeyDanceAcademy.pda_backend.entity.UserRegistration;
import pandeyDanceAcademy.pda_backend.global.constants.Const;
import pandeyDanceAcademy.pda_backend.repository.RegisteredUserRepo;
import pandeyDanceAcademy.pda_backend.repository.UserRegistrationRepo;
import pandeyDanceAcademy.pda_backend.service.implementation.EmailSendingService;
import pandeyDanceAcademy.pda_backend.utlity.Generator;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);
	private PasswordEncoder passwordEncoderBCrypt;

	@Autowired EmailDetails eDetails;
	@Autowired RegisteredUser registeredUser;
	@Autowired private EmailSendingService emailService;
	@Autowired private UserRegistrationRepo userRegistrationRepo;
	@Autowired private RegisteredUserRepo registeredUserRepo;
	
	// For JWT
	private UserDetailsService userDetailsService;
	private AuthenticationManager authenticationManager;
	private final JWTHelper jwtHelper;

	public AuthController(AuthenticationManager authenticationManager, JWTHelper jwtHelper, UserDetailsService userDetailsService, PasswordEncoder passwordEncoderBCrypt) {
		this.authenticationManager = authenticationManager;
		this.jwtHelper = jwtHelper;
		this.userDetailsService = userDetailsService;
		this.passwordEncoderBCrypt = passwordEncoderBCrypt;
	}

	@PostMapping({"/getToken", "/login"})
	public ResponseEntity<Map<String, Object>> loginAndGetToken(@Valid @RequestBody LoginReqBody body) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		UserDetails userDetail = userDetailsService.loadUserByUsername(body.getUserName());
		if(!passwordEncoderBCrypt.matches(body.getPassword(), userDetail.getPassword())) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("MESSAGE","Incorrect password"));

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getUserName(), body.getPassword(), userDetail.getAuthorities()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
//	        UserDetails userDetail2 = (UserDetails) authentication.getPrincipal();
	        String newToken = jwtHelper.generateToken(userDetail);

	        response.put("token", newToken);
	        response.put("expiresIn", Const.JWT_TOKEN_VALIDITY);
	        response.put("expiresIn Unit", Const.JWT_TOKEN_VALIDITY_UNIT_S);
			response.put("user", Map.of("username",userDetail.getUsername(), "authorities", userDetail.getAuthorities()));

		} catch (BadCredentialsException bce) {
			throw new RuntimeErrorException(new Error(), "Invalid username or password!! ");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(value={"/logout","/releaseToken"})
    public ResponseEntity<Map<String, String>> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(Map.of("MEssage", "Logged out successfully"));
    }

	@PostMapping("addNewUser")
	public ResponseEntity<String> sendOTPForRegistration(@Valid @RequestBody UserRegistration userReg) {
		Optional<RegisteredUser> alreadyRegisteredUser = registeredUserRepo.findByEmailID(userReg.getEmailID());
		if (alreadyRegisteredUser.isEmpty()) {
			String otpString = Generator.generateNumberedPassword(5);
			userReg.setOtp(otpString);
			String subject = "Holla Loop Login OPT " + otpString;
			eDetails.setRecipientEmail(userReg.getEmailID());
			eDetails.setSubject(subject);
			StringBuilder body = new StringBuilder();
			body.append("""
					<html>
						<body>
							<h1>Welcome to the hoola loop</h1>
							<p>This is your loging OTP:<br/>
							<b>""" + userReg.getOtp() + """
									</b>
							</p>
						</body>
					</html>""");
			eDetails.setMsgBody(body.toString());
			boolean mailSent = emailService.sendSimpleMailWithHTMLContent(eDetails);
			if (mailSent == true) {
				Optional<UserRegistration> byEmailID = userRegistrationRepo.findByEmailID(userReg.getEmailID());
				if (byEmailID.isPresent()) {
					UserRegistration registeredUser = byEmailID.get();
					registeredUser.setExpireDateTime(
							Instant.now().plus(Duration.ofMinutes(Constant_Num.ONE_TWENTY)).toString());
					userRegistrationRepo.save(registeredUser);
				} else
					userRegistrationRepo.save(userReg);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(Constant_String.SUCCESS);
			} else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constant_String.FAILED);
		} else
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(Constant_String.USER_EXISTS);
	}

	@PostMapping("verifyNewUser")
	public ResponseEntity<String> verifyOTPForRegistration(@Valid @RequestBody UserRegistration userReg) {
		System.out.println(userReg);

		if (userReg.getId() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID missing!");
		if (userReg.getOtp() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP to verify is missing!");

		Optional<UserRegistration> byId = userRegistrationRepo.findById(userReg.getId());
		if (byId.isPresent()) {
			System.out.println(byId);
			if (byId.get().getOtp().equals(userReg.getOtp()) == true) {
				registeredUser.setEmailID(byId.get().getEmailID());
				registeredUserRepo.save(registeredUser);
				userRegistrationRepo.delete(byId.get());
				return ResponseEntity.status(HttpStatus.CREATED).body(Constant_String.SUCCESS);
			} else
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OTP not matched");

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constant_String.Not_Found);
		}
	}

	@GetMapping("loginReq")
	@Transactional
	public void loignRequest(@RequestParam(name = "mailID", required = true) String mailID) {
		System.out.println(mailID + " req to loign");

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
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			var ex1 = (HttpRequestMethodNotSupportedException) ex;
			errors.put("Message", ex1.getMessage());
		} else {
			var ex1 = (Exception) ex;
			errors.put("Message: ", ex1.getMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}