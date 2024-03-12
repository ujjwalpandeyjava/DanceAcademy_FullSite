package pandeyDanceAcademy.pda_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.entity.EmailDetails;

@Repository
public interface EmailRepo extends MongoRepository<EmailDetails, String> {

}
