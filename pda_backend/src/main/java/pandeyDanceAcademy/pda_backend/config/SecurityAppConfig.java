package pandeyDanceAcademy.pda_backend.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity(debug = true) // remove debug	// enables web security support
//@ComponentScan(basePackages = {"pandeyDanceAcademy.pda_backend.config"})
public class SecurityAppConfig {
	
	
	@Bean
	public InMemoryUserDetailsManager setUpUser(){
		

		GrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
		GrantedAuthority User = new SimpleGrantedAuthority("USER");
		GrantedAuthority Visitor = new SimpleGrantedAuthority("VISITOR");
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(Visitor);
		authorities.add(User);
		authorities.add(admin);
		UserDetails userUjjwal = new User("ujjwalpandey", "ujjwalpandey", authorities);	// Store in DB 
		
//		JdbcUserDetailsManager - InMemoryUserDetailsManager
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		inMemoryUserDetailsManager.createUser(userUjjwal);
		return inMemoryUserDetailsManager;
	}
	
	// We used bcz we did not had any password encoder and with this line we removed encoder necessity.
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	/*
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(cors -> cors.disable()) // Cross-Origin Resource Sharing
				.csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery
				.authorizeHttpRequests(authorize -> {
					authorize.requestMatchers("", "/", "/home", "/index").permitAll();
					authorize.requestMatchers("/api/v1/admissionQuery").permitAll();
					authorize.anyRequest().authenticated();
				})
				.oauth2Login(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults())
				.build();
		
	}

	/*
	
//	 Hard coded users working fine for formLogin
//	@Bean
//	public UserDetailsService hardCodedUsers() {
//		UserDetails admin = org.springframework.security.core.userdetails.User.withUsername("AdminPandey").password(new BCryptPasswordEncoder(11).encode("123")).roles("ADMIN").build();
//		UserDetails simple = org.springframework.security.core.userdetails.User.withUsername("SimplePandey").password(new BCryptPasswordEncoder(11).encode("123")).roles("SIMPLE").build();
//		return new InMemoryUserDetailsManager(admin, simple);
//	}
	
	@Bean
	public CorsConfigurationSource corsConfigSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("https://example.com", "http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	*/
}
