package service;
import lombok.Getter;
import lombok.Setter;
import model.UserProfile;
import model.VacationPackage;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserProfileRepository;
import repository.VacationPackageRepository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class UserProfileService {
    private String duplicateUserError;
    private String constraintsError;
    private String packageAlreadyAddedError;

    public void newUser(String username, String plainPassword) {
        try {
            UserProfileRepository.create(username, BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
        } catch(javax.persistence.PersistenceException pe) {
            setDuplicateUserError("Username taken!");
        } catch(javax.validation.ConstraintViolationException cve) {
            setConstraintsError("Size must be between 6 and 16!");
        }
    }

    public void bookPackage(Long user_id, Long vacation_package_id) {
        try {
            UserProfileRepository.addPackage(user_id, vacation_package_id);
        } catch(java.lang.IllegalStateException ise) {
            setPackageAlreadyAddedError("Package Already added!");
        }
    }

    public static List<VacationPackage> getBookings(Long id) {
       return UserProfileRepository.getVacationPackages(id);
    }
}
