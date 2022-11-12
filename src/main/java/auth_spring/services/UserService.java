package auth_spring.services;

import auth_spring.model.User;
import auth_spring.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LogManager.getLogger(UserService.class.getName());


    public List<User> getUsers(){
        return userRepository.getAll();
    }

      public User updateName(Integer id, String name) {
        logger.info("updateName");
        Optional<User> userById = userRepository.getUserById(id);

        if (!userById.isPresent()) {
            throw new NullPointerException("user not found");
        }

        if (userById.get().getName().equals(name)) {
            throw new IllegalArgumentException("can not update the same name");
        }

        // need to improve - write the field only
        userById.get().setName(name);
        return userRepository.updateUser(userById.get());
    }

    public User updateEmail(Integer id, String email) {
        logger.info("updateEmail");
        Optional<User> userById = userRepository.getUserById(id);

        if (!userById.isPresent()) {
            throw new NullPointerException("user not found");
        }

        if (userById.get().getEmail().equals(email)) {
            throw new IllegalArgumentException("can not update the same email");
        }

        // need to improve - write the field only
        userById.get().setEmail(email);
        return userRepository.updateUser(userById.get());
    }

    public User updatePassword(Integer id, String password) {
        logger.info("updatePassword");
        Optional<User> userById = userRepository.getUserById(id);

        if (!userById.isPresent()) {
            throw new NullPointerException("user not found");
        }

        if (userById.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("can not update the same password");
        }

        // need to improve - write the field only
        userById.get().setPassword(password);
        return userRepository.updateUser(userById.get());
    }

    public void removeUser(Integer id) {
        logger.info("removeUser");

        Optional<User> userById = userRepository.getUserById(id);
        if (!userById.isPresent()) {
            throw new NullPointerException("user not found");
        }
        userRepository.removeUserFromDb(id);
    }

}
