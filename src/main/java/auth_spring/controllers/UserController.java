package auth_spring.controllers;

import auth_spring.AuthSpring;
import auth_spring.model.User;
import auth_spring.services.AuthService;
import auth_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
