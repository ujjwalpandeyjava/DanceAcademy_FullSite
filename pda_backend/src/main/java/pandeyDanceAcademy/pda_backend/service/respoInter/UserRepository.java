package pandeyDanceAcademy.pda_backend.service.respoInter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pandeyDanceAcademy.pda_backend.entity.Users;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

//	Optional<Users> findByUsername(String emailID);

	Users findByUsername(String emailID);

}
