package auth_spring.controllers;

import auth_spring.controllers.requests.UserRequest;
import auth_spring.controllers.responses.ErrorMessageResponse;
import auth_spring.controllers.responses.TokenResponse;
import auth_spring.services.AuthService;
import auth_spring.services.UserService;
import auth_spring.utils.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    private static Logger logger = LogManager.getLogger(AuthController.class.getName());


    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest){
        try {
            logger.info("addUser");
            boolean isValidateUser = Validate.validateUserFields(userRequest.getEmail(), userRequest.getName(), userRequest.getPassword());

            if (!isValidateUser) {
                logger.error("input fields are not valid, registration failed");
                return ResponseEntity.badRequest().body(new ErrorMessageResponse("input fields are not valid, registration failed"));
            }

            return ResponseEntity.ok(authService.add(userRequest));
        } catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));        // already exist
        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity login(@RequestBody UserRequest userRequest){
        try {
            logger.info("login");
            boolean isValidateUser = Validate.validateUserFields(userRequest.getEmail(), userRequest.getPassword());

            if (!isValidateUser) {
                logger.error("input fields are not valid, login failed");
                return ResponseEntity.badRequest().body(new ErrorMessageResponse("input fields are not valid, login failed"));
            }

            return ResponseEntity.ok(new TokenResponse(authService.login(userRequest)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));        // password doesn't match
        } catch (NullPointerException ex) {
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));        // user not found
        }
    }
}
