package Exceptions;

public class EmptyHistoryCartException extends Exception {
    public EmptyHistoryCartException() {
        super("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
    }
}
