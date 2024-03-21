package pandeyDanceAcademy.pda_backend.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@EnableWebSecurity(debug = false) // remove debug	// enables web security support
//@ComponentScan(basePackages = {"pandeyDanceAcademy.pda_backend.config"})
public class SecurityAppConfig {

//	@Autowired private PasswordEncoder passwordEncoderBCrypt;
//	@Autowired UserDetailsService userDetailService;
	
	@Autowired private JWTAuthenticationEntryPoint jwtEntryPoint;
	@Autowired private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	public SecurityAppConfig(JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

	// CORS filter , create and use multipe (one with one) securityFilterChain and CorsCongifurationSource for different sites access allowed method...
	@Bean
	public UrlBasedCorsConfigurationSource  corsConfigurationSource() {

		CorsConfiguration corConfigs = new CorsConfiguration();
		corConfigs.setAllowCredentials(true);

		// No need to mention @CrossOrign at class level.
		// This filter will handle the CORS (Cross Origin Resource Sharing).
		corConfigs.setAllowedOrigins(Arrays.asList("http://localhost:3001"));
		corConfigs.addAllowedOrigin("http://localhost:3000");


//		configuration.setAllowedMethods(Arrays.asList("*"));
//		configuration.setAllowedHeaders(Arrays.asList("*"));
		
		
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
		urlBasedCorsConfigSrc.registerCorsConfiguration("/**", corConfigs);
		return urlBasedCorsConfigSrc;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//				.cors(Customizer.withDefaults())
//				.cors(cors -> cors.disable())
				.csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery - disabled to avoid complexities
				.authorizeHttpRequests(authorize -> {
//					authorize.requestMatchers("/admin").hasAnyRole("ADMIN", "SUPER_ADMIN");
					authorize.requestMatchers( "/index", "/home","/api/v1/auth/getToken", "/api/v1/admissionQuery/save").permitAll();
					authorize.anyRequest().authenticated();
				})
				.exceptionHandling(eh -> eh.authenticationEntryPoint(jwtEntryPoint))
				.sessionManagement(sessMag -> sessMag.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		 return http.build();
	}

//	@Bean 
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider dap = new DaoAuthenticationProvider(passwordEncoderBCrypt);
////		dap.setUserDetailsService(userDetailService); // currently null;
//		return dap;
//	}

}
