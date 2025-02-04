package bookstore.model;

import bookstore.utils.FileUtils;

import java.util.List;

public class AccountManager {
    private static final String USER_FILE = "data/users.txt";

    public static String login(String username, String password) {
        List<String> users = FileUtils.readFile(USER_FILE);
        for (String user : users) {
            String[] parts = user.split("\\|");
            if (parts.length == 3 && parts[1].equals(username) && parts[2].equals(password)) {
                return parts[0]; // "admin" หรือ "user"
            }
        }
        return "invalid";
    }
}
