import java.util.Arrays;

public abstract class User {
    protected String userName;
    protected String password;
    private static final int SIZE_INCREASE = 2;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public static boolean isExist(User[] users, String inputUserName, int numberOfUsers) {
        for (int i = 0; i < numberOfUsers; i++) {
            if (users[i].getUserName().equalsIgnoreCase(inputUserName)) return true;
        }
        return false;
    }
}
