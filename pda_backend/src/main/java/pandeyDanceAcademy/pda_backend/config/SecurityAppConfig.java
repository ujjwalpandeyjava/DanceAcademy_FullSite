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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pandeyDanceAcademy.pda_backend.config.JWT.JWTAuthenticationEntryPoint;
import pandeyDanceAcademy.pda_backend.config.JWT.JWTAuthenticationFilter;
import pandeyDanceAcademy.pda_backend.global.constants.UserAuthorities_Types;

@Configuration
@EnableWebSecurity
public class SecurityAppConfig {

	private JWTAuthenticationEntryPoint jwtEntryPoint;
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	public SecurityAppConfig(JWTAuthenticationEntryPoint jwtEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter,
			PasswordEncoder pase) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtEntryPoint = jwtEntryPoint;
	}

	// CORS filter , create and use multiple (one with one) securityFilterChain and
	// CorsCongifurationSource for different sites access allowed method...
	@Bean
//	@Order(1)
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration corConfigs = new CorsConfiguration();
		corConfigs.setAllowCredentials(true);

		corConfigs.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080", "http://127.0.0.1:5500"));

		corConfigs.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));

		corConfigs.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT));

		corConfigs.setMaxAge(Duration.ofMinutes(20));

		UrlBasedCorsConfigurationSource urlBasedCorsConfigSrc = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigSrc.registerCorsConfiguration("/**", corConfigs);
		return urlBasedCorsConfigSrc;
	}

	@Bean
//	@Order(0)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> {
//					authorize.requestMatchers("/**").permitAll();	// For testings
					authorize.requestMatchers(HttpMethod.GET, "/", "index", "home", "error", "/currentUser", "/redirect", "/api/v1/classes/", "api/v2/classes/**").permitAll();
					authorize.requestMatchers(HttpMethod.GET, "/api/v1/classes/").permitAll();
					authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/getToken").permitAll();
					authorize.requestMatchers(HttpMethod.GET, "/api/v1/auth/verifyNewUser").permitAll();
					authorize.requestMatchers(HttpMethod.POST, "/api/v1/admissionQuery/save", "/api/v1/query/save").permitAll();
					authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/addNewUser", "/api/v1/classes").hasAnyAuthority(UserAuthorities_Types.ADMIN);
					authorize.anyRequest().authenticated();
				}).exceptionHandling(eh -> eh.authenticationEntryPoint(jwtEntryPoint))
				.sessionManagement(sessMag -> sessMag.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

//	@Autowired
//	  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	    auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//	  }
}