package pandeyDanceAcademy.pda_backend.service.respoInter;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;

import pandeyDanceAcademy.pda_backend.entity.Registrations;

@RestController
public interface RegistrationsRepo extends MongoRepository<Registrations, String> {

	Optional<Registrations> findByEmailID(String emailID);

}
