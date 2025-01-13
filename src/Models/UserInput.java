package Models;
import java.util.Scanner;

public class UserInput {
    private static final Scanner sc = new Scanner(System.in);
    private static UserInput instance;

    public static UserInput getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new UserInput();
        return instance;
    }

    public String getString() {
        String input;
        do input = sc.nextLine();
        while (input.isEmpty());
        return input;
    }

    public int getInt() {
        int choice = 0;
        boolean error = true;
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

    public double getDouble(){
        double choice = 0;
        boolean error;
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


    public String getUserName(){
        System.out.println("Enter username: (Enter -1 to return main menu)");
        return getString();
    }

    public String getPassword(){
        System.out.println("Enter password: (Enter -1 to return main menu)");
        return getString();
    }


    public String getStreet(){
        System.out.println("Street: (Enter -1 to return main menu)");
        return getString();
    }

    public String getHouseNum(){
        System.out.println("House number: (Enter -1 to return main menu)");
        return getString();
    }

    public String getCity(){
        System.out.println("City: (Enter -1 to return main menu)");
        return getString();
    }


    public String getState(){
        System.out.println("State: (Enter -1 to return main menu)");
        return getString();
    }

    public String getProductName(){
        System.out.println("Enter product name to add: (Enter -1 to return main menu)");
        return getString();
    }


    public double getProductPrice(){
        System.out.println("Enter product price: (Enter -1 to return main menu)");
        return getDouble();
    }

    public double getSpecialPackagePrice(){
        System.out.println("Enter price for special package: (Enter -1 to return main menu)");
        return getDouble();
    }

    public int getProductCategory(){
        System.out.println("Choose category: (Enter -1 to return main menu)\n");
        return getInt();
    }

    public void closeScanner()
    {
        sc.close();
    }
}
