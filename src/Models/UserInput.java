package Models;

import java.util.Scanner;

public class UserInput {
    private static final Scanner sc = new Scanner(System.in);

    public String getString() {
        String input;
        do input = sc.nextLine();
        while (input.isEmpty());
        return input;
    }

    public int getInt() {
        int choice = 0;
        boolean error;
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
}
