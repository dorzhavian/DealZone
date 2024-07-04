// DOR_ZHAVIAN-211337845_ALON_ETOS-207431487_DANIEL_SULTAN-323883751
// LECTURER - PINI SHLOMI

import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static Manager manager;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        manager = new Manager();
        menu(manager);
        sc.close();
    }

    private static void menu(Manager manager) {
        int choice;
        System.out.println("------------------------------------------------------------------------");
        System.out.println(" ------------HELLO AND WELCOME TO OUR BUYER - SELLER PROGRAM-----------");
        System.out.println(" ------------(In anytime press -1 for return to main menu)-------------");
        System.out.println("------------------------------------------------------------------------");
        do {
            printMenu();
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    case1();
                    break;
                case 2:
                    case2();
                    break;
                case 3:
                    case3();
                    break;
                case 4:
                    case4();
                    break;
                case 5:
                    case5();
                    break;
                case 6:
                    manager.printBuyersInfo();
                    break;
                case 7:
                    manager.printSellersInfo();
                    break;
            }
        } while (choice != 0);
        System.out.println("Thanks for using our system. GoodBye!");
    }

    private static void printMenu() {
        System.out.println("Menu : ");
        System.out.println("0) Exit");
        System.out.println("1) Add seller");
        System.out.println("2) Add buyer");
        System.out.println("3) Add item for seller");
        System.out.println("4) Add item for buyer");
        System.out.println("5) Payment for buyer");
        System.out.println("6) Buyer's details");
        System.out.println("7) Seller's details");
        System.out.println("Please enter your choice: ");
    }

    public static void case1 () {
        String username;
        String password;
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            username = sc.nextLine();
            if (username.equals("-1")) return ;
        } while (!manager.isExist(username, "seller"));
        System.out.println("Enter password: (Enter -1 to return main menu)");
        password = sc.nextLine();
        if (password.equals("-1")) return;
        manager.addSeller(username, password);
    }

    public static void case2 () {
        String username;
        String password;
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            username = sc.nextLine();
            if (username.equals("-1")) return ;
        } while (!manager.isExist(username, "buyer"));
        System.out.println("Enter password: (Enter -1 to return main menu)");
        password = sc.nextLine();
        if (password.equals("-1")) return ;
        System.out.println("Enter your address: (Enter -1 to return main menu)");
        String address = sc.nextLine();
        if (address.equals("-1")) return ;
        manager.addBuyer(username, password, address);
    }

    public static void case3 () {
        if (manager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet.");
            return;
        }
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        System.out.println("Enter product name to add: (Enter -1 to return main menu)");
        String productName = sc.nextLine();
        if (productName.equals("-1")) return ;
        System.out.println("Enter product price: (Enter -1 to return main menu)");
        double productPrice = sc.nextDouble();
        if (productPrice < 0) return ;
        manager.addProductSeller(sellerIndex, productName, productPrice);
    }

    public static void case4 () {
        if (manager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet. return to main menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        if (manager.getSellers()[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller haven't products to sell yet.");
            return;
        }
        System.out.println(manager.getSellers()[sellerIndex].toString()); // print the chosen seller products
        System.out.println("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
        int productIndex = sc.nextInt() - 1;
        sc.nextLine();
        if (productIndex < 0) return;
        manager.addProductBuyer(buyerIndex,sellerIndex,productIndex);
    }

    public static void case5 () {
        if (manager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet. return to main menu.");
            return;
        }
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (manager.getBuyers()[buyerIndex].getCurrentCart().getNumOfProducts() == 0) {
            System.out.println("Cart is empty, cannot process to checkout.");
            return; }
        System.out.println(manager.getBuyers()[buyerIndex].getCurrentCart().toString());
        System.out.println("Are you sure you want to process checkout? Enter YES/NO");
        String answer = sc.nextLine();
        if (answer.equalsIgnoreCase("no")) return;
        manager.pay(buyerIndex);
    }

    public static int chooseSeller () {
        System.out.println("Seller's list: ");
        for (int i = 0; i < manager.getNumberOfSellers(); i++) {
            System.out.println(i+1 + ")" + manager.getSellers()[i].getUserName());
        }
        System.out.println("Please choose seller from list by number: 1-" + manager.getNumberOfSellers() + " (Enter -1 to return main menu)");
        int choiceSeller = sc.nextInt();
        sc.nextLine();
        if (choiceSeller == -1) return -1;    // return to main menu
        return choiceSeller - 1;
    }

    public static int chooseBuyer () {
        System.out.println("Buyer's list: ");
        for (int i = 0; i < manager.getNumberOfBuyers(); i++) {
            System.out.println(i+1 + ")" + manager.getBuyers()[i].getUserName());
        }
        System.out.println("Please choose buyer from list by number: 1-" + manager.getNumberOfBuyers() + " (Enter -1 to return main menu)");
        int choiceBuyer = sc.nextInt();
        sc.nextLine();
        if (choiceBuyer == -1) return -1;    // return to main menu
        return choiceBuyer - 1;
    }
}
