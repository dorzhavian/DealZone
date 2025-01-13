package Models;

public class Menu {

    public void printMenu() {
        System.out.println("\nMenu : ");
        System.out.println("0) Exit");
        System.out.println("1) Add seller");
        System.out.println("2) Add buyer");
        System.out.println("3) Add item for seller");
        System.out.println("4) Add item for buyer");
        System.out.println("5) Payment for buyer");
        System.out.println("6) Buyer's details");
        System.out.println("7) Seller's details");
        System.out.println("8) Product's by category");
        System.out.println("9) Replace current cart with cart from history");
        System.out.println("10) Use factory for automatic shop create");
        System.out.println("99) Question 15");
        System.out.println("100) Question 16");
        System.out.println("101) Question 17 ");
        System.out.println("102) Question 18 ");
        System.out.println("103) Question 19 ");
        System.out.println("Please enter your choice: ");
    }

    public void start() {
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println(" ------------HELLO AND WELCOME TO OUR BUYER - SELLER PROGRAM-----------");
        System.out.println(" ------------(In anytime press -1 for return to main menu)-------------");
        System.out.println("------------------------------------------------------------------------");
    }
}
