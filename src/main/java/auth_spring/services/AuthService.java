package auth_spring.services;

import auth_spring.controllers.requests.UserRequest;
import auth_spring.controllers.responses.TokenResponse;
import auth_spring.model.User;
import auth_spring.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static auth_spring.utils.Utils.generateUniqueToken;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    static HashMap<String, String> mapUserTokens = new HashMap<>();

    private static Logger logger = LogManager.getLogger(AuthService.class.getName());


    public User add(UserRequest userRequest) {
        logger.info("add");
        User userExist = UserRepository.getUserByEmail(userRequest.getEmail());

        if (userExist != null) {
            logger.error("The user already exists");
            throw new IllegalArgumentException("The user already exists");
        }

        User user = new User(userRequest.getEmail(), userRequest.getName(), userRequest.getPassword());
        return userRepository.add(user);
    }

    public TokenResponse login(UserRequest userRequest) {
        User userByEmail = userRepository.getUserByEmail(userRequest.getEmail());

        if (userByEmail == null) {
            throw new NullPointerException("user not found");
        }

        if (userByEmail.getPassword().equals(userRequest.getPassword())) {
            String token = generateUniqueToken();
            mapUserTokens.put(token, String.valueOf(userByEmail.getId()));
//            return token;
            return new TokenResponse(token, userByEmail);
        } else {
            logger.error("password doesn't match");
            throw new IllegalArgumentException("password doesn't match");
        }
    }

    public Integer getUserIdByToken(String token)  {
        String id = mapUserTokens.get(token);

        if (id == null) {
            throw new NullPointerException("user not authorized");
        }
        return Integer.valueOf(id);
    }
}
