package pandeyDanceAcademy.pda_backend.entity;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Document
public class RegisteredUser {
	private String id;
	@NotBlank
	private String emailID;
	private String createdDateTime = Instant.now().toString();
	private String fName;
	private String lName;
}
