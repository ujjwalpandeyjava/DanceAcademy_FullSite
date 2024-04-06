package pandeyDanceAcademy.pda_backend.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
public class Classes {
	@Id
	private String id;
	@NotNull(message = "Start Time is required!")
	private Date batchStartDateTime;
	@Min(30)
	@Max(180)
	private int durationInMin;
	private String note;
}
