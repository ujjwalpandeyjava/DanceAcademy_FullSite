package pandeyDanceAcademy.pda_backend.controllers;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pandeyDanceAcademy.pda_backend.entity.Query;
import pandeyDanceAcademy.pda_backend.global.constants.Const;
import pandeyDanceAcademy.pda_backend.global.constants.QueryStatuses;
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
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(Const.MESSAGE, Const.SUCCESS, "Query", queryRepo.save(body)));
	}

	@GetMapping()
	public Object getAllQueriesObject() {
		logger.info("Inside all queries...");
		return ResponseEntity.status(HttpStatus.OK).body(queryRepo.findAll());
	}

	@PatchMapping("/{id}")
	public Object updateQuery(@PathVariable("id") String id, @RequestParam("newStatus") String newStatus) {
		logger.info("updaing query... {}, newStatus {}", id, newStatus);
		Optional<Query> query = queryRepo.findById(id);
		if (query.isPresent()) {
			Query queryToU = query.get();
			queryToU.setStatus(newStatus);
			if(newStatus.equals(QueryStatuses.New)) {
				queryToU.setSolvedDateTime(null);
			}else {
				queryToU.setSolvedDateTime(Instant.now().toString());
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(Const.MESSAGE, "Quey status changes to '" + newStatus +"' successfully!", "User", queryRepo.save(queryToU)));
		} else 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(Const.MESSAGE, "Query not found!"));
	}

}