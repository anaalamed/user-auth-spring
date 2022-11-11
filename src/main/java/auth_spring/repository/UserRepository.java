package auth_spring.repository;
import auth_spring.model.User;
import auth_spring.utils.Files;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserRepository  {
    private static Map<Integer, User> users = new HashMap<>();     // cache
    private static final String BASE_ROUTE = "src/main/java/auth_spring/repository/repo";


    public UserRepository() {
        users = cacheUsersFilesFromRepo();                         // cache all users to map
    }

    public List<User> getAll(){
        return new ArrayList<>(users.values());
    }

    public static User getUserByEmail(String email){
        for (Integer i : users.keySet()) {
            if (users.get(i).getEmail().equals(email)) {
                return users.get(i);
            }
        }
        return null;
    }

    public User getUserById(int id){
        for (Integer i : users.keySet()) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    public User add(User user) {
            String filename = BASE_ROUTE + "/" + user.getId() + ".json";
            User userAdded = Files.add(filename, user);
            users.put(userAdded.getId(), userAdded);
            return userAdded;
    }

    public User updateUser(User user) {
            String filename = BASE_ROUTE + "/" + user.getId() + ".json";
            User userUpdated = Files.add(filename, user);
            users.put(userUpdated.getId(), userUpdated);
            return userUpdated;
    }

    public void removeUserFromDb(int id) {
        String filename = BASE_ROUTE + "/" + id + ".json";
        Files.removeFile(filename);
        users.remove(id);
    }

    private static Map<Integer, User> cacheUsersFilesFromRepo() {
        File folder = new File(BASE_ROUTE);
        File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                User user = Files.readFromFile(BASE_ROUTE + "/" + listOfFiles[i].getName());
                users.put(Integer.valueOf(user.getId()), user);
            }
        return users;
    }
}
