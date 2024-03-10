package pandeyDanceAcademy.pda_backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.model.CustomerQueryEntity;

@Repository
public interface AdimissionQueryRepo extends MongoRepository<CustomerQueryEntity, String>{

}
