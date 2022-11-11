package auth_spring.utils;

import auth_spring.model.User;
import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;

public class Files {
    private static Gson gson = new Gson();


    public static void writeToFile(String filename, String  content) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException ex) {
            throw new RuntimeException("could not write to file");
        }
    }

    public static User add(String filename, User user) {
        try {
            writeToFile(filename, gson.toJson(user));
            return user;
        } catch (RuntimeException ex) {
            throw new RuntimeException("user add failed " + ex);
        }
    }

    public static void removeFile(String filename){
        try{
            File file = new File(filename);
            if (file.delete()) {
                System.out.println("Deleted the file: " + file.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
        catch (NullPointerException ex){
            System.out.println("File was not found");
        }
    }


    // read from file
//    public static HashMap<String, String> readFromFile(String filename) {
//        try (Reader reader = new FileReader(filename)) {
//
//            // Convert JSON File to HashMap
//            HashMap<String, String> mapJson = gson.fromJson(reader, HashMap.class);
//            return mapJson;
//
//        } catch (FileNotFoundException e) {
//            System.out.println("FileNotFoundException ex: "+ e);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static User readFromFile(String filename) {
        try (Reader reader = new FileReader(filename)) {

            User user = gson.fromJson(reader, User.class);
            return user;

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException ex: "+ e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
