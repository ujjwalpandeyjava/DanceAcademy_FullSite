package pandeyDanceAcademy.pda_backend.config.JWT.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTRequest {
	private String userName;
	private String password;
}
