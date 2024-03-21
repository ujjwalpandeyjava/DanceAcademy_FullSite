package pandeyDanceAcademy.pda_backend.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class _MasterController {

	@GetMapping(value = {"index","home" })
	public String getHomeURL() {
		System.out.println("Getting current user..");
		return "At home page";
	}
	
	@GetMapping("/currentUser")
	public Object oneUser(Principal principle) { // Principal represents current user

		return principle;
	}
}
