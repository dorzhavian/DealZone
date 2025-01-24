package Models;

import Enums.ExceptionsMessages;

import java.util.Scanner;

public class UserInput {
    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String message) {
        String input;
        do {
            System.out.println(message + " (Enter -1 to return to the main menu) ");
            input = sc.nextLine().trim();
            if (input.equals("-1")) return null;
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter again!");
            }
        } while (input.isEmpty());
        return input;
    }

    public static int getInt(String message) {
        int choice;
        while (true) {
            System.out.println(message + " (Enter -1 to return to the main menu) ");
            try {
                choice = Integer.parseInt(sc.next());
                if (choice == -1) return -1;
                else {
                    sc.nextLine();
                    return choice;
                }
            } catch (Exception e) {
                System.out.println(ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage());
                sc.nextLine();
            }
        }
    }

    public static double getDouble(String message) {
        double choice;
        while (true) {
            System.out.println(message + " (Enter -1 to return to the main menu) ");
            try {
                choice = Double.parseDouble(sc.next());
                if (choice == -1) return -1;
                else {
                    sc.nextLine();
                    return choice;
                }
            } catch (Exception e) {
                System.out.println(ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage());
                sc.nextLine();
            }
        }
    }

    public static boolean getYesNo(String message) {
        String input;
        do {
            System.out.println(message + " (Enter -1 to return to the main menu) ");
            input = sc.next().trim().toLowerCase();
            sc.nextLine();
            if (input.equals("-1")) return false;
            if (input.equalsIgnoreCase("y")) return true;
            if(input.isEmpty())
                System.out.println("Input cannot be empty. Please enter again!");
        } while (input.isEmpty());
        return false;
    }

    public static void close() {
        sc.close();
    }
}

