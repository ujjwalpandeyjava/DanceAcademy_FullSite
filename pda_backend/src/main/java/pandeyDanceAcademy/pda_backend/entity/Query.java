package pandeyDanceAcademy.pda_backend.entity;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pandeyDanceAcademy.pda_backend.global.constants.QueryStatuses;

@Document
@Data
public class Query {
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	private String contact;
	@NotBlank
	private String description;
	private String status = QueryStatuses.New;
	private String createdDateTime = Instant.now().toString();
	private String solvedDateTime;
}
