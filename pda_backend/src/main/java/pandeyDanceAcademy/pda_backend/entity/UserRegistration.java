package pandeyDanceAcademy.pda_backend.entity;

import java.time.Duration;
import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pandeyDanceAcademy.pda_backend.global.constants.Const;

@Data
@Document
public class UserRegistration {
	private String id;
	@NotBlank
	private String emailID;
	@NotBlank
	private String defaultPassword;
	private String otp;
	private String authorities = "FACULTY";
	private String registrationDateTime = Instant.now().toString();
	private String expireDateTime = Instant.now().plus(Duration.ofMinutes(Const.ONE_TWENTY)).toString();
}
