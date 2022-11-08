package auth_spring.controllers;

import auth_spring.AuthSpring;
import auth_spring.model.User;
import auth_spring.services.AuthService;
import auth_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
//    @Autowired
//    private AuthService authService;
    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeUser(@RequestHeader String token) {
            Integer userId = AuthService.getUserId(token);
            UserService.removeUser(userId);
            return ResponseEntity.noContent().build();
    }
}
