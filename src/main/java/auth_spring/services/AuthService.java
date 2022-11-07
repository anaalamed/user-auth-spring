package auth_spring.services;

import auth_spring.controllers.requests.UserRequest;
import auth_spring.model.User;
import auth_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static auth_spring.utils.Utils.generateUniqueToken;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    static HashMap<String, String> mapUserTokens = new HashMap<>();

    public User add(UserRequest userRequest) {
        User user = new User(userRequest.getEmail(), userRequest.getName(), userRequest.getPassword());
        return userRepository.add(user);
    }

    public String login(UserRequest userRequest) {
        User userByEmail = userRepository.getUserByEmail(userRequest.getEmail());
        System.out.println(userByEmail);

        if (userByEmail.getPassword().equals(userRequest.getPassword())) {
            String token = generateUniqueToken();
            mapUserTokens.put(token, String.valueOf(userByEmail.getId()));
            return token;

        }
        return null;
    }
}
