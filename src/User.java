import Exceptions.AlreadyExistException;
import Exceptions.EmptyException;
import Exceptions.OnlyNumbersUserNameException;

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
        if (Manager.isNumeric(inputUserName)) throw new OnlyNumbersUserNameException("User");
    }

    public static void isValidPassword (String inputPass) throws EmptyException {
        if (inputPass == null || inputPass.trim().isEmpty()) throw new EmptyException("Password");
    }

    public static void isExist(User[] users, String inputUserName, int numberOfUsers) throws AlreadyExistException {
        for (int i = 0; i < numberOfUsers; i++) {
            if (users[i].getUserName().equalsIgnoreCase(inputUserName)) throw new AlreadyExistException();
        }
    }
}
