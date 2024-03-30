package pandeyDanceAcademy.pda_backend.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class _MasterController {
	
	private Logger logger = LoggerFactory.getLogger(_MasterController.class);
	
	@Value("${ClientLogin}")
	private String redirectURL;

	@GetMapping(value = { "/", "index", "home" })
	public String getHomeURL() {
		System.out.println("Getting current user..");
		return "At home page";
	}

	@GetMapping("/currentUser")
	public Object oneUser(Principal principle) { // Principal represents current user
		return principle != null ? principle : "work";
	}

	@GetMapping("/redirect")
	public ResponseEntity<String> myApi(HttpServletRequest request) {
		String originUrl = request.getHeader("Origin");
	    logger.debug("Received request at agent  {}", request.getHeader("Origin"));
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, originUrl+"/ind.html").body("Redirecting to " + originUrl);
//		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, redirectURL).body("Redirecting to " + redirectURL);
	}
}
