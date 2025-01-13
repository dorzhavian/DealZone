package Models;
import java.util.Scanner;

public class UserInput implements InputHandler {
    private static final Scanner sc = new Scanner(System.in);
    private static UserInput instance;

    public static UserInput getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new UserInput();
        return instance;
    }

    public String getString(String message) {
        String input;
        System.out.println(message);
        do input = sc.nextLine();
        while (input.isEmpty());
        return input;
    }

    public int getInt(String message) {
        int choice = 0;
        boolean error = true;
        System.out.println(message);
        do {
            try {
                error = true;
                choice = Integer.parseInt(sc.next());
            } catch (Exception e) {
                System.out.println("\nInvalid input, please enter a digit!");
                error = false;
            }
        } while (!error);
        sc.nextLine();
        return choice;
    }

    public double getDouble(String message){
        double choice = 0;
        boolean error;
        System.out.println(message);
        do {
            try {
                error = true;
                choice = Double.parseDouble(sc.next());
            } catch (Exception e) {
                System.out.println("\nInvalid input, please enter a digit!");
                error = false;
            }
        } while (!error);
        sc.nextLine();
        return choice;
    }

    public void close()
    {
        sc.close();
    }
}
