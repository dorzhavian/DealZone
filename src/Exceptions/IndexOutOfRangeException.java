package Exceptions;

public class IndexOutOfRangeException extends Exception{
    public IndexOutOfRangeException(String useType) {
        super("\n" + useType + " number is NOT exist, PLEASE choose from the RANGE!\n");
    }
}
