package pandeyDanceAcademy.pda_backend.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pandeyDanceAcademy.pda_backend.entity.Query;
import pandeyDanceAcademy.pda_backend.global.constants.Const;
import pandeyDanceAcademy.pda_backend.service.respoInter.QueryRepo;

@RestController
@RequestMapping("/api/v1/query")
public class QueryController {

	private QueryRepo queryRepo;

	Logger logger = LoggerFactory.getLogger(QueryController.class);

	public QueryController(QueryRepo queryRepo) {
		this.queryRepo = queryRepo;
	}

	@PostMapping("/save")
	public ResponseEntity<Map<Object, Object>> addCustomerQuery(@Valid @RequestBody Query body) {
		logger.info("Request body: {}", body);
		@Valid
		Query savedQuery = queryRepo.save(body);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of(Const.MESSAGE, Const.SUCCESS, "Query", savedQuery));
	}

}