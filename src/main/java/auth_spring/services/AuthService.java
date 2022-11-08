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
        try {
            User userExist = UserRepository.getUserByEmail(userRequest.getEmail());

            if (userExist != null) {
                throw new IllegalArgumentException("The user already exists");
            }

            User user = new User(userRequest.getEmail(), userRequest.getName(), userRequest.getPassword());
            return userRepository.add(user);
        } catch (RuntimeException ex) {
            throw new NullPointerException("the user wasn't added");
        }

    }

    public String login(UserRequest userRequest) {
        User userByEmail = userRepository.getUserByEmail(userRequest.getEmail());

        if (userByEmail.getPassword().equals(userRequest.getPassword())) {
            String token = generateUniqueToken();
            mapUserTokens.put(token, String.valueOf(userByEmail.getId()));
            return token;
        }
        throw new IllegalArgumentException("login failed");
    }

    public static Integer getUserId(String token)  {
        String id = mapUserTokens.get(token);

        if (id == null) {
            throw new NullPointerException();
        }
        return Integer.valueOf(id);
    }
}
