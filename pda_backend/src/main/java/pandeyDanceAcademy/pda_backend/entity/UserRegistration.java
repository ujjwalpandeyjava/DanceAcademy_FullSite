package pandeyDanceAcademy.pda_backend.entity;

import java.time.Duration;
import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Document
public class UserRegistration {
	private String id;
	@NotBlank
	private String emailID;
	private String registrationDateTime;
	private String expireDateTime;

	@PrePersist
	private void setDateTimes() {
		Instant now = Instant.now();
		this.registrationDateTime = now.toString();
		this.expireDateTime = now.plus(Duration.ofMinutes(120)).toString();
	}
}
