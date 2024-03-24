package pandeyDanceAcademy.pda_backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Document
@Data
public class Query {
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	private String contact;
	@NotBlank
	private String description;
	private String status;
	private String createdDateTime;
	private String solvedDateTime;
}
