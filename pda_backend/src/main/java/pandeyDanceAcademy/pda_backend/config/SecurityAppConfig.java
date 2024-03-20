package pandeyDanceAcademy.pda_backend.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true) // remove debug	// enables web security support
//@ComponentScan(basePackages = {"pandeyDanceAcademy.pda_backend.config"})
public class SecurityAppConfig {

	@Autowired
	PasswordEncoder passwordEncoderBCrypt;
	
//	@Autowired
//	UserDetailsService userDetailService;
	
	
/*	
	// CORS filter , create and use multipe (one with one) securityFilterChain and CorsCongifurationSource for different sites access allowed method...
	@Bean
	@Order(0)
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
	*/
@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
//				.cors(cors ->{
//					cors.configurationSource(corsConfigurationSource());
//				})
//				.cors(Customizer.withDefaults())
				.cors(cors->{
					cors.disable();
				})
				.csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery - disabled to avoid complexities
				.authorizeHttpRequests(authorize -> {
					authorize.requestMatchers( "/", "/home", "/index").permitAll();
					authorize.requestMatchers("/api/v1/admissionQuery").permitAll();
					authorize.anyRequest().authenticated();
				})
//				.oauth2Login(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults())
				.build();
	}
/*	
	@Bean 
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider(passwordEncoderBCrypt);
//		dap.setUserDetailsService(userDetailService); // currently null;
		return dap;
	}*/
	
//	Hard coded users working fine for formLogin
	@Bean
	public InMemoryUserDetailsManager setUpUser_HardCodedUsers() {
//		GrantedAuthority user = new SimpleGrantedAuthority("USER");
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(visitor);
	
		UserDetails userUjjwal = User.builder().username("ujjwal").password(new BCryptPasswordEncoder(11).encode("123")).authorities("USER").build();
		UserDetails userPandey = User.builder().username("pandey").password(new BCryptPasswordEncoder(11).encode("123")).authorities("USER", "ADMIN", "VISITOR").build();
		return new InMemoryUserDetailsManager(userUjjwal, userPandey);	// JdbcUserDetailsManager - InMemoryUserDetailsManager
	}
}
