package pandeyDanceAcademy.pda_backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("emailSent")
@Builder
public class EmailDetails {
	@Id
	private String id;
	@NotBlank(message = "Whome to send?")
	private String recipientEmail;
	@NotBlank(message = "Provide subject")
	@NotNull
	private String subject;
	@NotNull
	@NotBlank(message = "What is the body?")
	private String msgBody;
	private String attachment;
}
