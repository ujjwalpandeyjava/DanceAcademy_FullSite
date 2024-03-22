package pandeyDanceAcademy.pda_backend.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pandeyDanceAcademy.pda_backend.config.JWT.JWTAuthenticationEntryPoint;
import pandeyDanceAcademy.pda_backend.config.JWT.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityAppConfig {

	
	private JWTAuthenticationEntryPoint jwtEntryPoint;
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	public SecurityAppConfig(JWTAuthenticationEntryPoint jwtEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtEntryPoint = jwtEntryPoint;
    }

	// CORS filter , create and use multiple (one with one) securityFilterChain and CorsCongifurationSource for different sites access allowed method...
	@Bean
	public UrlBasedCorsConfigurationSource  corsConfigurationSource() {

		CorsConfiguration corConfigs = new CorsConfiguration();
		corConfigs.setAllowCredentials(true);

		corConfigs.setAllowedOrigins(Arrays.asList("http://localhost:3001"));
		corConfigs.addAllowedOrigin("http://localhost:3000");

		corConfigs.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()
			));

		corConfigs.setAllowedHeaders(List.of(
				HttpHeaders.AUTHORIZATION,
				HttpHeaders.CONTENT_TYPE,
				HttpHeaders.ACCEPT
			));

		corConfigs.setMaxAge(Duration.ofMinutes(20));
		
		UrlBasedCorsConfigurationSource urlBasedCorsConfigSrc = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigSrc.registerCorsConfiguration("**", corConfigs);
		return urlBasedCorsConfigSrc;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> {
				authorize.requestMatchers(HttpMethod.GET, "/", "index", "home").permitAll();
				authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/verifyNewUser").permitAll();
				authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/addNewUser").hasAnyAuthority("ADMIN");
				authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/getToken", "/api/v1/admissionQuery/save").permitAll();
				authorize.anyRequest().authenticated();
			})
			.exceptionHandling(eh -> eh.authenticationEntryPoint(jwtEntryPoint))
			.sessionManagement(sessMag -> sessMag.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		 return http.build();
	}
}