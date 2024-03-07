package pandeyDanceAcademy.pda_backend.controllers;

import org.bson.json.JsonObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

	@PostMapping
	public JsonObject saveQuery() {
		return null;
	}
}
