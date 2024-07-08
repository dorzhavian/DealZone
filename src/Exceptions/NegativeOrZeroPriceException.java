package Exceptions;

public class NegativeOrZeroPriceException extends Exception{
    public NegativeOrZeroPriceException() {
        super("Price cannot be negative or zero, please try again!");
    }
}
