package pandeyDanceAcademy.pda_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.entity.UserRegistration;

@Configuration
public class BeansConfigs {

	@Bean
	@Scope("prototype")
	public EmailDetails getEmailDetails() {
		return new EmailDetails();
	}

	@Bean
	@Scope("prototype")
	public UserRegistration getUserRegistration() {
		return new UserRegistration();
	}

	/*@Bean
	@Scope("prototype")
	public RegisteredUser getRegisteredUser() {
		return new RegisteredUser();
	}*/

	@Bean
	public PasswordEncoder passwordEncoderBCrypt() {
		return new BCryptPasswordEncoder(11);
	}

//	@Bean
//	public UserDetailsService userDetailsService () {
//		return null; // add our UserDetailServiceImpl for crud...
//	}	
	
	
//	Hard coded users working fine for formLogin
	@Bean
	public UserDetailsService setUpUser_HardCodedUsers() {
//		GrantedAuthority user = new SimpleGrantedAuthority("USER");
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(visitor);
	
		UserDetails userPandey = User.builder().username("admin").password(passwordEncoderBCrypt().encode("123")).authorities("ADMIN").build();
		UserDetails userUjjwal = User.builder().username("faculty").password(passwordEncoderBCrypt().encode("123")).authorities("FACULTY").build();
		return new InMemoryUserDetailsManager(userUjjwal, userPandey);	// JdbcUserDetailsManager - InMemoryUserDetailsManager
	}
	
	@Bean
	public AuthenticationManager AuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}
