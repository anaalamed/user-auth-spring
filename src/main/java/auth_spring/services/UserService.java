package auth_spring.services;

import auth_spring.model.User;
import auth_spring.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User user = userRepository.getUserById(id);

        if (user == null) {
            throw new NullPointerException("user not found");
        }

        if (user.getName().equals(name)) {
            throw new IllegalArgumentException("can not update the same name");
        }

        // need to improve - write the field only
        user.setName(name);
        return userRepository.updateUser(user);
    }

    public void removeUser(Integer id) {
        logger.info("removeUser");

        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new NullPointerException("user not found");
        }
        userRepository.removeUserFromDb(id);
    }

}
