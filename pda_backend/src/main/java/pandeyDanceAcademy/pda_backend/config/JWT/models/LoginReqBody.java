package pandeyDanceAcademy.pda_backend.config.JWT.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginReqBody {
	@NotBlank
	private String userName;
	@NotBlank
	private String password;
}
