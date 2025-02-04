package bookstore.model;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ✅ Getter & Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ✅ Authentication
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Admin: " + username;
    }
}
