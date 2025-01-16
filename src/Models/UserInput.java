package Models;
import java.util.Scanner;

public class UserInput {
    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String message) {
        String input;
        System.out.println(message);
        do input = sc.nextLine();
        while (input.isEmpty());
        return input;
    }

    public static int getInt(String message) {
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

    public static double getDouble(String message){
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

    public static void close()
    {
        sc.close();
    }
}
