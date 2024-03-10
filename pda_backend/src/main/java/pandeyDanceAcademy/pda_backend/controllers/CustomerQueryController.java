package pandeyDanceAcademy.pda_backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customerQuery")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class CustomerQueryController {
	
	@PostMapping
	public void addCustomerQuery() {
		
	}
	

}
