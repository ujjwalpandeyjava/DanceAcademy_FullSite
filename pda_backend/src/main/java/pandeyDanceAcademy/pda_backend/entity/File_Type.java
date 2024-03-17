package pandeyDanceAcademy.pda_backend.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class File_Type {
	private String type;
	private String originalName;
	private String filePath;
}
