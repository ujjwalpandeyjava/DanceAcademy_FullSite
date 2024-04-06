package pandeyDanceAcademy.pda_backend.service.inter;

import org.springframework.http.ResponseEntity;

import pandeyDanceAcademy.pda_backend.entity.Classes;

public interface ClassesServiceInter {

	ResponseEntity<Classes> addClass(Classes classDetail);

	ResponseEntity<Object> getClassesForUI();

	ResponseEntity<Object> getNewBatchesForAdminPage();

	ResponseEntity<Object> deleteClass(String id);

	ResponseEntity<Object> getPastBatchesForAdminPage();

}
