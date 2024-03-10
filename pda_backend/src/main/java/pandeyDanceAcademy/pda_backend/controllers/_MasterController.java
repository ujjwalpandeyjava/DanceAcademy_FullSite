package pandeyDanceAcademy.pda_backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class _MasterController {

	@GetMapping(value = { "", "/" })
	public String getHomeURL() {
		return "At home page";
	}
}
