package pandeyDanceAcademy.pda_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class _MasterController {

	@GetMapping(value = { "", "/" })
	public String getHomeURL() {
		return "At home page";
	}
}
