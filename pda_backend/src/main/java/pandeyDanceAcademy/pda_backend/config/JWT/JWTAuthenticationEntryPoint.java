package pandeyDanceAcademy.pda_backend.config.JWT;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Value("${ClientLogin}")
	private String clientLoginUrl;

//	private final ObjectMapper objectMapper;

	public JWTAuthenticationEntryPoint(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
	}

	// Exception handler for JWT
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// Without this line response will be a simple string.
		response.setContentType("application/json");

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("path", request.getRequestURI());
		responseBody.put("method", request.getMethod());
		responseBody.put("error", "Access denied");
		responseBody.put("message", authException.getMessage());
		System.out.println(authException.getMessage());

//        response.sendRedirect(clientLoginUrl);
		new ObjectMapper().writeValue(response.getWriter(), responseBody);

	}

}
