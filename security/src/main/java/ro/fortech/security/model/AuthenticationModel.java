package ro.fortech.security.model;

public class AuthenticationModel {

    private String username;

    private String password;

    public AuthenticationModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationModel() {
    }

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
}
