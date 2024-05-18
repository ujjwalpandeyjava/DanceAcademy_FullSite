package pandeyDanceAcademy.pda_backend.controllers;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pandeyDanceAcademy.pda_backend.global.Utlity;

@RestController
public class _MasterController {

	private Logger logger = LoggerFactory.getLogger(_MasterController.class);

	@Value("${ClientLogin}")
	private String redirectURL;

	@GetMapping(value = { "/", "index", "home" })
	public ResponseEntity<String> getHomeURL(HttpServletRequest request, HttpServletResponse response) {
		Cookie oldCookie = Utlity.getCookie(request, "lastVisit");
		var lastVisit = ResponseCookie
				.from("lastVisit")
				.value(Instant.now().toString())
				.maxAge(Duration.ofDays(500))
				.path("/")
				.httpOnly(true)
				.build();
	    String lastLastVisit = "";
		if(oldCookie != null) {
			lastLastVisit =	ResponseCookie
				.from("lastLastVisit")
				.value(oldCookie.getValue())
				.maxAge(Duration.ofDays(500))
				.path("/")
				.httpOnly(true).build().toString();
		}
		
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.SET_COOKIE, lastVisit.toString())
				.header(HttpHeaders.SET_COOKIE, lastLastVisit.toString())
				.body("At home page!");
	}

	@GetMapping("/currentUser")
	public Object oneUser(Principal principle) { // Principal represents current user
		return principle != null ? principle : "work";
	}

	@GetMapping("/redirect")
	public ResponseEntity<String> myApi(HttpServletRequest request) {
		String originUrl = request.getHeader("Origin");
		logger.debug("Received request at agent  {}", request.getHeader("Origin"));
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
				.header(HttpHeaders.LOCATION, originUrl + "/ind.html").body("Redirecting to " + originUrl);
//		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, redirectURL).body("Redirecting to " + redirectURL);
	}
}
