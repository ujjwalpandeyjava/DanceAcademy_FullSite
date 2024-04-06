package pandeyDanceAcademy.pda_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.entity.Registrations;

@Configuration
public class BeansConfigs {

	@Bean
	@Scope("prototype")
	public EmailDetails getEmailDetails() {
		return new EmailDetails();
	}

	@Bean
	@Scope("prototype")
	public Registrations getUserRegistration() {
		return new Registrations();
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

//	@Autowired
//	private DataSource dataSource;
//	@Bean
//	public UserDetailsService userDetailsService () {
//		JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//	    manager.setDataSource(dataSource);
//	    return manager;
//		return null; // add our UserDetailServiceImpl for crud...
//	}	
	
	
//	Hard coded users working fine for formLogin
//	@Bean
//	public UserDetailsService setUpUser_HardCodedUsers() {
////		GrantedAuthority user = new SimpleGrantedAuthority("USER");
////		List<GrantedAuthority> authorities = new ArrayList<>();
////		authorities.add(visitor);
//	
//		// In-Memory style
//		UserDetails userPandey = User.builder().username("admin").password(passwordEncoderBCrypt().encode("123")).authorities(UserAuthorities_Types.ADMIN).build();
//		UserDetails userUjjwal = User.builder().username("faculty").password(passwordEncoderBCrypt().encode("123")).authorities(UserAuthorities_Types.FACULTY).build();
//		return new InMemoryUserDetailsManager(userUjjwal, userPandey);	// JdbcUserDetailsManager - InMemoryUserDetailsManager
//		
//		// DB style
//	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}
