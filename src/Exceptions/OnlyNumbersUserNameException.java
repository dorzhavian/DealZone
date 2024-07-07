package Exceptions;

public class OnlyNumbersUserNameException extends Exception{
    public OnlyNumbersUserNameException(){
        super("Name cannot be only numbers, please try again!");
    }
}
