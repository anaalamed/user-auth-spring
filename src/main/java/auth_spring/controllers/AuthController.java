package auth_spring.controllers;

import auth_spring.AuthSpring;
import auth_spring.controllers.requests.UserRequest;
import auth_spring.controllers.responses.TokenResponse;
import auth_spring.model.User;
import auth_spring.services.AuthService;
import auth_spring.services.UserService;
import auth_spring.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest){
        boolean isValidateUser = Validate.validateUserFields(userRequest.getEmail(), userRequest.getName(), userRequest.getPassword());

        if (!isValidateUser) {
            throw new IllegalArgumentException("input is not valid, registration failed");
        }

        return ResponseEntity.ok(authService.add(userRequest));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity login(@RequestBody UserRequest userRequest){
        boolean isValidateUser = Validate.validateUserFields(userRequest.getEmail(), userRequest.getPassword());

        if (!isValidateUser) {
            throw new IllegalArgumentException("input is not valid, login failed");
        }

        return ResponseEntity.ok(new TokenResponse(authService.login(userRequest)));
    }





}
