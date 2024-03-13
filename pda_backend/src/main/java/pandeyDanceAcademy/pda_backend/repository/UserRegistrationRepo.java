package pandeyDanceAcademy.pda_backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;

import pandeyDanceAcademy.pda_backend.entity.UserRegistration;

@RestController
public interface UserRegistrationRepo extends MongoRepository<UserRegistration, String> {

	Optional<UserRegistration> findByEmailID(String emailID);

}
