package auth_spring.services;

import auth_spring.model.User;
import auth_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public List<User> getUsers(){
        return userRepository.getAll();
    }
}
