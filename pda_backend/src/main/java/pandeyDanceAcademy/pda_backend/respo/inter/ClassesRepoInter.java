package pandeyDanceAcademy.pda_backend.respo.inter;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.entity.Classes;

@Repository
public interface ClassesRepoInter extends MongoRepository<Classes, String> {

	List<Classes> findAllByBatchStartDateTimeAfter(Date date, Sort sort);

	List<Classes> findAllByBatchStartDateTimeBefore(Date date, Sort sort);
	
	Page<Classes> findAllByBatchStartDateTimeBefore(Date date, Pageable pageable);

}
