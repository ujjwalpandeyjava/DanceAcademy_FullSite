package pandeyDanceAcademy.pda_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
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

}
