package pandeyDanceAcademy.pda_backend.entity;

import java.time.Instant;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Setter;

@Document
@Setter
public class Users extends User {

	private static final long serialVersionUID = 1L;

	private String id;
	private String createdDateTime = Instant.now().toString();
	//	To add more fields create another entity and attach it with this ones id, and add all there
	
	public Users(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}	

	// Cause error
//	public Users(String id, String createdDateTime,  String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//		this.id = id;
//		this.createdDateTime = createdDateTime;
//	}
//	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
}
