package pandeyDanceAcademy.pda_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.entity.RegisteredUser;
import pandeyDanceAcademy.pda_backend.entity.UserRegistration;

@Configuration
public class BeansConfigs {

	@Bean
	public EmailDetails getEmailDetails() {
		return new EmailDetails();
	}

	@Bean
	public UserRegistration getUserRegistration() {
		return new UserRegistration();
	}

	@Bean
	public RegisteredUser getRegisteredUser() {
		return new RegisteredUser();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoderBCrypt() {
		return new BCryptPasswordEncoder(11);
	}	
	
//	@Bean
//	public UserDetailsService userDetailsService () {
//		return null; // add our UserDetailServiceImpl for crud...
//	}

}
