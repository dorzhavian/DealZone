package Models;

import Exceptions.AlreadyExistException;

public abstract class User {
    protected String userName;
    protected String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public static void isExist(User[] users, String inputUserName, int numberOfUsers) throws AlreadyExistException {
        for (int i = 0; i < numberOfUsers; i++) {
            if (users[i].getUserName().equalsIgnoreCase(inputUserName)) throw new AlreadyExistException();
        }
    }
}
