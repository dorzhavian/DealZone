package Exceptions;

public class AlreadyExistException extends Exception{
    public AlreadyExistException(){
        super("Name already exist, please try again!");
    }
}
