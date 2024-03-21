package pandeyDanceAcademy.pda_backend.config.JWT.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTResponse {
	private String token;
	private String userName;
}
