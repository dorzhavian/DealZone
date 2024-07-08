package Exceptions;

public class YesNoChoiceException extends Exception {
    public YesNoChoiceException(){
        super("Please choose YES or NO only!");
    }
}
