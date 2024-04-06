package pandeyDanceAcademy.pda_backend.respo.inter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.entity.Query;

@Repository
public interface QueryRepo extends MongoRepository<Query, String>{

}
