package pandeyDanceAcademy.pda_backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.entity.RegisteredUser;

@Repository
public interface RegisteredUserRepo extends MongoRepository<RegisteredUser, String> {

//	Optional<RegisteredUser> findByEmailID(String emailID);
	Optional<RegisteredUser> findByUsername(String emailID);

}
