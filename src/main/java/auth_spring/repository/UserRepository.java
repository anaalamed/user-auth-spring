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
    private static Map<Integer, User> users;     // cache
    private static final String BASE_ROUTE = "src/main/java/auth_spring/repository/repo";


    public UserRepository() {
        users = new HashMap<>();
    }

    public List<User> getAll(){
//        cacheUsersFilesFromRepo();
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

    public static User getUserById(int id){
        for (Integer i : users.keySet()) {
            if (users.get(i).getEmail().equals(id)) {
                return users.get(i);
            }
        }
        return null;
    }

    public User add(User user) {
        try {
            String filename = BASE_ROUTE + "/" + user.getId() + ".json";
            User userAdded = Files.add(filename, user);
            users.put(userAdded.getId(), userAdded);
            return userAdded;
        } catch (RuntimeException ex) {
            throw new RuntimeException("user wasn't added: " + ex);
        }
    }

    public static void removeUserFromDb(int id) {
        try {
        String filename = BASE_ROUTE + "/" + id + ".json";
        Files.removeFile(filename);
        users.remove(id);
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("file wasn't found");
        }
    }

    private static Map<Integer, User> cacheUsersFilesFromRepo() {
        File folder = new File(BASE_ROUTE);
        File[] listOfFiles = folder.listFiles();

        String filename = BASE_ROUTE + "/";

        for (int i = 0; i < listOfFiles.length; i++) {
            HashMap<String, String> fileContent = Files.readFromFile(filename + listOfFiles[i].getName());
            users.put(Integer.valueOf(fileContent.get("id")), new User(fileContent.get("email"), fileContent.get("name"), fileContent.get("password")));
        }
        return users;
    }
}
