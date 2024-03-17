package pandeyDanceAcademy.pda_backend.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("admissionQueries")
public class CustomerQueryEntity {
	@Id
	private String id;
	private String name;
	@NotBlank(message = "Must specify gender")
	private String gender;
	@NotBlank(message = "DanceForm must be filled")
	private String danceForm;
	private String email;
	@NotBlank(message = "Contact must be given for connecting purpose")
	private String contactNo;
	private String guardianContactNo;
	private String address;
	private String description;
	private Date createdDate;
	private String status;
	private Binary imageBase64;
	private List<File_Type> files;
}
