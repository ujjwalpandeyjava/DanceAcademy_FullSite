package pandeyDanceAcademy.pda_backend.controllers;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pandeyDanceAcademy.pda_backend.config.JWT.JWTHelper;
import pandeyDanceAcademy.pda_backend.config.JWT.models.LoginReqBody;
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
@CrossOrigin("https://localhost:3000")
public class AuthController {
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	private PasswordEncoder passwordEncoderBCrypt;
	private EmailDetails eDetails;
	private EmailSendingService emailService;
	private UserRegistrationRepo userRegistrationRepo;
	private RegisteredUserRepo registeredUserRepo;
	private UserDetailsService userDetailsService;
	private AuthenticationManager authenticationManager;
	private final JWTHelper jwtHelper;

	public AuthController(AuthenticationManager authenticationManager, JWTHelper jwtHelper,
			UserDetailsService userDetailsService, PasswordEncoder passwordEncoderBCrypt, EmailDetails eDetails,
			EmailSendingService emailService, UserRegistrationRepo userRegistrationRepo,
			RegisteredUserRepo registeredUserRepo) {
		this.authenticationManager = authenticationManager;
		this.jwtHelper = jwtHelper;
		this.userDetailsService = userDetailsService;
		this.passwordEncoderBCrypt = passwordEncoderBCrypt;
		this.eDetails = eDetails;
		this.emailService = emailService;
		this.userRegistrationRepo = userRegistrationRepo;
		this.registeredUserRepo = registeredUserRepo;
	}

	/**
	 * Register user with userName and a password provided by the administration,
	 * User are created by administration, account get verified when the user
	 * verifies with the OTP the account get registered for the user...
	 * 
	 * @param userReg
	 * @return
	 */
	@PostMapping("addNewUser")
	@Transactional
	public ResponseEntity<Map<String, String>> sendOTPForRegistration(@Valid @RequestBody UserRegistration userReg) {
		Optional<RegisteredUser> alreadyRegisteredUser = registeredUserRepo.findByUsername(userReg.getEmailID());
		if (alreadyRegisteredUser.isPresent())
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(Map.of(Const.MESSAGE, Const.USER_EXISTS));
		else {
			String otpString = Generator.generateNumberedPassword(5);
			userReg.setOtp(otpString);

			eDetails.setRecipientEmail(userReg.getEmailID());
			String subject = "Holla Loop Login OPT " + otpString;
			eDetails.setSubject(subject);
			StringBuilder body = new StringBuilder();
			body.append("""
					<html>
						<body>
							<h1>Welcome to the hoola loop</h1>
							<p>This is your loging OTP:<br/>
							<b>""" + userReg.getOtp() + """
									</b>
							<p>Link with id...</p>
							</p>
						</body>
					</html>""");
			eDetails.setMsgBody(body.toString());
			boolean mailSent = emailService.sendSimpleMailWithHTMLContent(eDetails);

			if (mailSent == true) {
				UserRegistration savedDetails;
				Optional<UserRegistration> byEmailID = userRegistrationRepo.findByEmailID(userReg.getEmailID());
				if (byEmailID.isPresent()) {
					userReg = byEmailID.get();
					userReg.setOtp(otpString);
					userReg.setExpireDateTime(Instant.now().plus(Duration.ofMinutes(Const.ONE_TWENTY)).toString());
				}
				savedDetails = userRegistrationRepo.save(userReg);
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(Map.of(Const.MESSAGE, Const.SUCCESS, Const.Action,
								"Check provided email for link and OTP for verification. Valid for " + Const.ONE_TWENTY
										+ " minutes!",
								"UserID", savedDetails.getId()));
			} else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(Const.MESSAGE, Const.INCORRECT_EMAIL));
		}
	}

	@PostMapping("verifyNewUser")
	@Transactional
	public ResponseEntity<Map<String, String>> verifyOTPForRegistration(@Valid @RequestBody UserRegVerify userReg) {
		Optional<UserRegistration> verifyingUser = userRegistrationRepo.findById(userReg.getId());

		if (verifyingUser.isPresent()) { // Change to the registered user...
			UserRegistration verUser = verifyingUser.get();
			if (verUser.getOtp().equals(userReg.getOtp())) {

				String encodedPass = passwordEncoderBCrypt.encode(verUser.getDefaultPassword());
				RegisteredUser newUserDetails = new RegisteredUser(verUser.getEmailID(), encodedPass, true, true, true,
						true, List.of(new SimpleGrantedAuthority(verUser.getAuthorities())));
				newUserDetails.setId(verUser.getId());
				newUserDetails.setCreatedDateTime(Instant.now().toString());
				registeredUserRepo.save(newUserDetails);

				userRegistrationRepo.delete(verUser);
				return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(Const.MESSAGE, Const.SUCCESS));
			} else
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(Const.MESSAGE, "Incorrect OTP"));
		} else
			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
					.body(Map.of(Const.MESSAGE, "Record with id '" + userReg.getId() + "' not found!"));
	}

	@PostMapping({ "/getToken" })
	public ResponseEntity<Map<String, Object>> loginAndGetToken(@Valid @RequestBody LoginReqBody body) {
		System.err.println("inside get token..");
		HashMap<String, Object> response = new HashMap<String, Object>();
		UserDetails userDetail = userDetailsService.loadUserByUsername(body.getUserName());
		// If not found will throw error and @ExceptionHandler will send back the
		// response.

		if (!passwordEncoderBCrypt.matches(body.getPassword(), userDetail.getPassword()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(Const.MESSAGE, "Incorrect password"));

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					body.getUserName(), body.getPassword(), userDetail.getAuthorities()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
//	        UserDetails userDetail2 = (UserDetails) authentication.getPrincipal();
			String newToken = jwtHelper.generateToken(userDetail);

			response.put("token", newToken);
			response.put("expiresIn", Const.JWT_TOKEN_VALIDITY);
			response.put("unit", Const.JWT_TOKEN_VALIDITY_UNIT_S);
			response.put("user",
					Map.of("username", userDetail.getUsername(), "authorities", userDetail.getAuthorities()));

		} catch (BadCredentialsException bce) {
			throw new RuntimeErrorException(new Error(), "Invalid username or password!! ");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader("Authorization") String token) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		String usernameFromToken = jwtHelper.getUsernameFromToken(token.split(" ")[1]);
		UserDetails userDetail = userDetailsService.loadUserByUsername(usernameFromToken);
		String newToken = jwtHelper.generateToken(userDetail);

		response.put("token", newToken);
		response.put("expiresIn", Const.JWT_TOKEN_VALIDITY);
		response.put("unit", Const.JWT_TOKEN_VALIDITY_UNIT_S);
		response.put("user", Map.of("username", userDetail.getUsername(), "authorities", userDetail.getAuthorities()));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * In a typical JWT setup, the server doesn’t store tokens after they are
	 * issued, so there’s no way to “delete” a token from the server. The token is
	 * stateless, meaning all the information it carries is contained within the
	 * token itself. Once a token is issued, it’s valid until its expiration time,
	 * unless the server implements a token blacklist.
	 * 
	 * 
	 * We can do these stuff to keep track of revoked jwt
	 * 1. Token Blacklisting
	 * 2. Token Versioning
	 * 3. Short Expiration Times
	 * 
	 * @param token
	 * @return
	 */
	@DeleteMapping(value = { "/revokeToken" })
	public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
		// The token is state-less (not stored anywhere) thus it will logout the user
		// once but when used again can be validated successfully
		SecurityContextHolder.getContext().setAuthentication(null);

		String usernameFromToken = jwtHelper.getUsernameFromToken(token.split(" ")[1]);
		UserDetails userDetail = userDetailsService.loadUserByUsername(usernameFromToken);
		String revokeToken = jwtHelper.revokeToken(userDetail.getUsername());

		return ResponseEntity.ok(Map.of(Const.MESSAGE, "Token revoked successfully -- " + revokeToken));
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, IOException.class,
			IllegalArgumentException.class, UsernameNotFoundException.class, Exception.class })
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
			errors.put(Const.MESSAGE, ex1.getMessage());
		} else if (ex instanceof HttpMessageNotReadableException) {
			var ex1 = (HttpMessageNotReadableException) ex;
			System.err.println(ex1.getMessage());
			errors.put(Const.MESSAGE, "Request Body missing!");
		} else if (ex instanceof IllegalArgumentException) {
			var ex1 = (IllegalArgumentException) ex;
			errors.put(Const.MESSAGE, ex1.getMessage());
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			var ex1 = (HttpRequestMethodNotSupportedException) ex;
			errors.put(Const.MESSAGE, ex1.getMessage());
		} else if (ex instanceof UsernameNotFoundException) {
			var ex1 = (UsernameNotFoundException) ex;
			errors.put(Const.MESSAGE, "User " + ex1.getMessage() + " not found!");
		} else {
			var ex1 = (Exception) ex;
			errors.put(Const.MESSAGE, ex1.getMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}

@Data
class UserRegVerify {
	@NotBlank
	private String id;
	@NotBlank
	private String otp;
}