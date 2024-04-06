package pandeyDanceAcademy.pda_backend.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pandeyDanceAcademy.pda_backend.entity.Classes;
import pandeyDanceAcademy.pda_backend.respo.inter.ClassesRepoInter;
import pandeyDanceAcademy.pda_backend.service.inter.ClassesServiceInter;

@Service
public class ClassesServiceImpl implements ClassesServiceInter {

	Logger logger = LoggerFactory.getLogger(ClassesServiceImpl.class);

	@Autowired
	private ClassesRepoInter classesRepoInter;

	@Override
	public ResponseEntity<Classes> addClass(Classes newClassDetail) {
		return ResponseEntity.status(HttpStatus.CREATED).body(classesRepoInter.save(newClassDetail));
	}

	@Override
	public ResponseEntity<Object> getClassesForUI() {
		List<Classes> futureClasses = classesRepoInter.findAllByBatchStartDateTimeAfter(new Date(), Sort.by(Sort.Direction.ASC, "batchStartDateTime"));
		Page<Classes> pastClasses = classesRepoInter.findAllByBatchStartDateTimeBefore(new Date(), PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "batchStartDateTime")));
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("upComing", futureClasses, "onGoing", pastClasses.getContent()));
	}

	@Override
	public ResponseEntity<Object> getNewBatchesForAdminPage() {
		List<Classes> futureClasses = classesRepoInter.findAllByBatchStartDateTimeAfter(new Date(), Sort.by(Sort.Direction.ASC, "batchStartDateTime"));
		return ResponseEntity.status(HttpStatus.OK).body(futureClasses);
	}

	@Override
	public ResponseEntity<Object> getPastBatchesForAdminPage() {
		List<Classes> pastClasses = classesRepoInter.findAllByBatchStartDateTimeBefore(new Date(), Sort.by(Sort.Direction.DESC, "batchStartDateTime"));
		return ResponseEntity.status(HttpStatus.OK).body(pastClasses);
	}

	@Override
	public ResponseEntity<Object> deleteClass(String id) {
		if (classesRepoInter.existsById(id)) {
			classesRepoInter.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully!");
		} else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Class with ID '" + id + "' not found!!");
	}

}
