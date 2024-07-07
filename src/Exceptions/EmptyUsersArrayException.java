package Exceptions;

public class EmptyUsersArrayException extends Exception{
    public EmptyUsersArrayException (String userType) {
        super("Haven't " + userType + " yet, cannot be proceed. return to Menu.");
    }
}
