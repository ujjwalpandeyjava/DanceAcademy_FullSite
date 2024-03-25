package pandeyDanceAcademy.pda_backend.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pandeyDanceAcademy.pda_backend.entity.Users;
import pandeyDanceAcademy.pda_backend.service.respoInter.UserRepository;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserRepository registeredUserRepo;

	public UserController(UserRepository registeredUserRepo) {
		this.registeredUserRepo = registeredUserRepo;
	}

	@GetMapping(value = "", consumes = {  }) // "application/x-www-form-urlencoded;charset=UTF-8"
	public Object getAllUsers(@RequestParam("abd") String abd) {
		logger.info("get All Users {}", abd);
		List<Users> allRUs = registeredUserRepo.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("users", allRUs));
	}
}