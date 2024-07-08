package Exceptions;

public class OnlyNumbersUserNameException extends Exception{
    public OnlyNumbersUserNameException(String type){
        super(type + " name cannot be only numbers, please try again!");
    }
}
