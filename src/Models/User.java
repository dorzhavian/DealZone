package Models;

import Exceptions.AlreadyExistException;
import Exceptions.EmptyException;
import Exceptions.OnlyNumbersUserNameException;
import Managers.Manager;

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

    public static void isValidUserName (String inputUserName) throws EmptyException, OnlyNumbersUserNameException {
        if (inputUserName == null || inputUserName.trim().isEmpty()) throw new EmptyException("Name");
        if (inputUserName.contains(" ")) throw new IllegalArgumentException("Name cannot contains space, please try again!");
        if (Manager.isNumeric(inputUserName)) throw new OnlyNumbersUserNameException("Models.User");
    }

    public static void isValidPassword (String inputPass) throws EmptyException {
        if (inputPass == null || inputPass.trim().isEmpty()) throw new EmptyException("Password");
        if (inputPass.contains(" ")) throw new IllegalArgumentException("Password cannot contains space, please try again!");
    }

    public static void isExist(User[] users, String inputUserName, int numberOfUsers) throws AlreadyExistException {
        for (int i = 0; i < numberOfUsers; i++) {
            if (users[i].getUserName().equalsIgnoreCase(inputUserName)) throw new AlreadyExistException();
        }
    }
}
