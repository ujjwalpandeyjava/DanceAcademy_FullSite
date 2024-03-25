package pandeyDanceAcademy.pda_backend.service.respoInter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.entity.CustomerQueryEntity;

@Repository
public interface AdimissionQueryRepo extends MongoRepository<CustomerQueryEntity, String>{

	Page<CustomerQueryEntity> findAllByStatus(PageRequest of, String status);

}
