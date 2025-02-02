import java.io.*;
import java.util.*;

class UserManager {
    private List<String> users = new ArrayList<>();

    public void addUser(String name, String email) {
        users.add(name + ", " + email);
    }

    public void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String user : users) {
                writer.write(user);
                writer.newLine();
            }
            System.out.println("Users saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void loadUsersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Loaded User: " + line);
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}

public class UserApp {
    public static void main(String[] args) {
        UserManager manager = new UserManager();
        manager.addUser("Alice", "alice@example.com");
        manager.addUser("Bob", "bob@example.com");

        String filename = "users.txt";
        manager.saveUsersToFile(filename);
        manager.loadUsersFromFile(filename);
    }
}
