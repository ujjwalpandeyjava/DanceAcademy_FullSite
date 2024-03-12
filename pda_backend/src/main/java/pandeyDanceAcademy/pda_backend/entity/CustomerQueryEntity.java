package pandeyDanceAcademy.pda_backend.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pandeyDanceAcademy.pda_backend.constants.QueryStatuses;

@Data
@Document("admissionQueries")
public class CustomerQueryEntity {
	@Id
	private String id;
	private String name;
	@NotNull(message = "Gender cannot be null")
	@NotBlank(message = "Must specify gender")
	private String gender;
	@NotNull(message = "DanceForm cannot be null")
	@NotBlank(message = "DanceForm must be filled")
	private String danceForm;
	private String email;
	@NotNull(message = "Contact cannot be null")
	@NotBlank(message = "Contact must be given for connecting purpose")
	private String contactNo;
	private String guardianContactNo;
	private String address;
	private String description;
	private Date createdDate;
	private String status = QueryStatuses.New;
}
