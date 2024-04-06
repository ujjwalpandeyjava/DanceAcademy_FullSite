package pandeyDanceAcademy.pda_backend.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	@Email
	private String recipientEmail;
	@NotBlank(message = "Provide subject/topic heading")
	private String subject;
	@NotBlank(message = "What message should we deliver")
	private String msgBody;
	private String attachment;
	@Builder.Default
	private Date createdDate = new Date();
}
