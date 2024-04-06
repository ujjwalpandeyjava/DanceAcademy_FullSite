package pandeyDanceAcademy.pda_backend.respo.inter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.entity.EmailDetails;

@Repository
public interface EmailDetailsRepo extends MongoRepository<EmailDetails, String> {

}
