package pandeyDanceAcademy.pda_backend.controllers;

import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pandeyDanceAcademy.pda_backend.enums.RESPONSE_STATUS;
import pandeyDanceAcademy.pda_backend.model.QueryModel;

@RestController
@RequestMapping("/query")
public class QueryController {

	@PostMapping
	public ResponseEntity<Object> saveQuery(@RequestBody(required = false) QueryModel qm) {
		if (qm == null)
			return ResponseEntity.badRequest()
					.body(new JSONObject().put(RESPONSE_STATUS.MESSAGE.getVal(), "Request body is missing").toMap());
		// Save the querys
		return null;
	}

	@GetMapping
	public String getQuery() {
		JSONObject put = new JSONObject().put("key", false);
		System.err.println(put);
		return put.toString();
	}
}
