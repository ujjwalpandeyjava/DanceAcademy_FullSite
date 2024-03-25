package pandeyDanceAcademy.pda_backend.entity;

import java.time.Duration;
import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pandeyDanceAcademy.pda_backend.global.constants.Const;
import pandeyDanceAcademy.pda_backend.global.constants.UserAuthorities_Types;

@Data
@Document
public class Registrations {
	private String id;
	@NotBlank
	private String emailID;
	@NotBlank
	private String defaultPassword;
	private String otp;
	private String authorities = UserAuthorities_Types.ADMIN;
	private String registrationDateTime = Instant.now().toString();
	private String expireDateTime = Instant.now().plus(Duration.ofMinutes(Const.ONE_TWENTY)).toString();
}
