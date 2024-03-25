package pandeyDanceAcademy.pda_backend.config.JWT;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Checks is the auth token present in the header? Runs once every request
 * before the DD Dispatcher servlet,
 * 
 * Steps: 1. Get the token from the request 2. Validate the token 3. GetUsername
 * from token 4. Load user associated with this token 5. Set authentication and
 * work further.
 */

@Component
@Order(0)
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	@Autowired private JWTHelper jwtHelper;
	@Autowired private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String reqHeader = request.getHeader("authorization");
		logger.info("request Header authorization {}", reqHeader);
		String username = null;
		String token = null;

		if (reqHeader != null && reqHeader.startsWith("Bearer")) {
			token = reqHeader.split(" ")[1];			
			try {
				username = this.jwtHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				logger.error("Illegal argument whil fetching the username!! Error: {}", e.getMessage());
//				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				logger.error("Token alread expired!! Error: {}", e.getMessage());
//				e.printStackTrace();
			} catch (MalformedJwtException e) {
				logger.error("Tempered token!! Error: {}", e.getMessage());
//				e.printStackTrace();
			} catch (Exception e) {
				logger.error("Generic error!! Error: {}", e.getMessage());
//				e.printStackTrace();
			}

		} else {
			logger.warn("Invalid header value");
		}

		// user hitting with token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetail = this.userDetailsService.loadUserByUsername(username);
			Boolean isTokenValid = this.jwtHelper.validateToken(token, userDetail);
			if (isTokenValid) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, userDetail.getAuthorities(), userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				logger.info("Token not valid with the logged-in user");
			}
		}

		
		filterChain.doFilter(request, response);
	}

}
