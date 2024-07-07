package Exceptions;

public class EmptyException extends Exception{
    public EmptyException(String str){
        super(str + " cannot be empty, please try again!");
    }
}
