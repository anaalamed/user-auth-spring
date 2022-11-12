package auth_spring.controllers;

import auth_spring.controllers.requests.UserRequest;
import auth_spring.controllers.responses.ErrorMessageResponse;
import auth_spring.model.User;
import auth_spring.services.AuthService;
import auth_spring.services.UserService;
import auth_spring.utils.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    private static Logger logger = LogManager.getLogger(UserController.class.getName());



    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/update/name")
    public ResponseEntity updateName(@RequestHeader String token, @RequestBody UserRequest userRequest) {
        try {
            logger.info("updateName");
            if ( userRequest.getName() == null || !Validate.validateName(userRequest.getName())) {
                logger.error("name not valid");
                return ResponseEntity.badRequest().body(new ErrorMessageResponse("name not valid"));
            }

            Integer userId = authService.getUserIdByToken(token);
            logger.debug("user id - " +  userId);
            return ResponseEntity.ok(userService.updateName(userId, userRequest.getName()));

        } catch (NullPointerException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));           // user not found (id) / not authrized (token)
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));           // the same name in DB
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/update/email")
    public ResponseEntity updateEmail(@RequestHeader String token, @RequestBody UserRequest userRequest) {
        try {
            logger.info("updateEmail");
            if (userRequest.getEmail() == null || !Validate.validateEmail(userRequest.getEmail())) {
                logger.error("email not valid");
                return ResponseEntity.badRequest().body(new ErrorMessageResponse("email not valid"));
            }

            Integer userId = authService.getUserIdByToken(token);
            logger.debug("user id - " +  userId);
            return ResponseEntity.ok(userService.updateEmail(userId, userRequest.getEmail()));

        } catch (NullPointerException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));           // user not found (id) / not authrized (token)
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));           // the same email in DB
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/update/password")
    public ResponseEntity updatePassword(@RequestHeader String token, @RequestBody UserRequest userRequest) {
        try {
            logger.info("updatePassword");
            if (userRequest.getPassword() == null || !Validate.validatePassword(userRequest.getPassword())) {
                logger.error("password not valid");
                return ResponseEntity.badRequest().body(new ErrorMessageResponse("password not valid"));
            }

            Integer userId = authService.getUserIdByToken(token);
            logger.debug("user id - " +  userId);
            return ResponseEntity.ok(userService.updatePassword(userId, userRequest.getPassword()));

        } catch (NullPointerException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));           // user not found (id) / not authrized (token)
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));           // the same password in DB
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity removeUser(@RequestHeader String token) {
        try {
            Integer userId = authService.getUserIdByToken(token);
            logger.debug("user id - " + userId);
            userService.removeUser(userId);
            return ResponseEntity.noContent().build();
        } catch (NullPointerException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(ex.getMessage()));           // user not found (id) / not authrized (token)
        }
    }
}
