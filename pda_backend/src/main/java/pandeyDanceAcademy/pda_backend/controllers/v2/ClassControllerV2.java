package pandeyDanceAcademy.pda_backend.controllers.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pandeyDanceAcademy.pda_backend.entity.Classes;
import pandeyDanceAcademy.pda_backend.entity.EmailDetails;
import pandeyDanceAcademy.pda_backend.service.inter.ClassesServiceInter;

@RestController
@RequestMapping("api/v2/classes")
public class ClassControllerV2 {
	Logger logger = LoggerFactory.getLogger(ClassControllerV2.class);
	@Autowired
	private ClassesServiceInter classesService;

	@Autowired
	private EmailDetails ed;

	@Autowired
	private EmailDetails ed2;

	@GetMapping(value = { "/" })
	public Object getClassesForUI() {
		System.out.println(ed);
		System.out.println(ed2);
		System.out.println(ed == ed2);
		return classesService.getClassesForUI();
	}

	@PostMapping("/addNewClass")
	public ResponseEntity<Classes> saveOneUpcomingClass(@Valid @RequestBody Classes classDetail) {
		return classesService.addClass(classDetail);
	}

	@GetMapping("/newBatches")
	public ResponseEntity<Object> getNewClassesForAdminEdit() {
		return classesService.getNewBatchesForAdminPage();
	}

	@GetMapping("/oldBatches")
	public ResponseEntity<Object> getPastClassesForAdminEdit() {
		return classesService.getPastBatchesForAdminPage();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteClass(@PathVariable("id") String id) {
		return classesService.deleteClass(id);
	}

}
