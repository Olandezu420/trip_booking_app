package service;
import lombok.Getter;
import lombok.Setter;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepository;

import java.util.Objects;

@Getter
@Setter
public class UserService {
    private String notFoundError;
    private String wrongPasswordError;

    public User login(String username, String plainPassword) {
        User user = null;
        try {
            user = UserRepository.findByName(username);
        } catch(javax.persistence.NoResultException nre) {
            this.setNotFoundError("User not found!");
        }
        if(user == null)
            return null;
        if (BCrypt.checkpw(plainPassword, user.getPassword_digest()))
            return user;
        else {
            this.setWrongPasswordError("Wrong password!");
            return null;
        }
    }

}
