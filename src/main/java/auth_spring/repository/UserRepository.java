package auth_spring.repository;
import auth_spring.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserRepository  {
    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
//        for (int i=0; i<1; i++) {
//            users.add(new User());
//        }
    }

    public List<User> getAll(){
        return users;
    }

    public User getUserByEmail(String email){
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
//        for (User user:users) {
//            if (user.getEmail().equals(email)) {
//                System.out.println(user);
//                return user;
//            }
//        }
//        return null;
    }

    public User add(User user) {
        users.add(user);
        return user;
    }
}
