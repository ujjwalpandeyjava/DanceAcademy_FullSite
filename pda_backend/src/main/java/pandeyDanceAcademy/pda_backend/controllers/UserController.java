package pandeyDanceAcademy.pda_backend.controllers;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.ToString;
import pandeyDanceAcademy.pda_backend.entity.Users;
import pandeyDanceAcademy.pda_backend.global.constants.Const;
import pandeyDanceAcademy.pda_backend.respo.inter.UserRepository;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserRepository registeredUserRepo;

	public UserController(UserRepository registeredUserRepo) {
		this.registeredUserRepo = registeredUserRepo;
	}

	@GetMapping(value = "")
	public Object getAllUsers(@RequestParam("abd") String abd) {
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("users", registeredUserRepo.findAll()));
	}

	@PatchMapping(value = "update/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }) // "application/x-www-form-urlencoded;charset=UTF-8"
	public Object updateUser(@PathVariable("id") String id, @Valid @RequestBody UpdatingData ud) {
		logger.info("Updating user with id '{}', updating data {}", id, ud);
		Optional<Users> user = registeredUserRepo.findById(id);
		if (user.isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(Const.MESSAGE, "User with id: '" + id + "' not found!"));
		else {
			Users uUs = user.get();
		
			Users updateUser2 = new Users(uUs.getUsername(), uUs.getPassword(), ud.isEnabled(), ud.isAccountNonExpired(), ud.isCredentialsNonExpired(), ud.isAccountNonLocked(), uUs.getAuthorities());
			updateUser2.setCreatedDateTime(uUs.getCreatedDateTime());
			updateUser2.setId(uUs.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(Const.MESSAGE, uUs.getUsername() +" updated Successfully",  "user", registeredUserRepo.save(updateUser2)));
		}
	}
}

@Data
@ToString
class UpdatingData {
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
}