//DOR_ZHAVIAN_211337845_&_ASAF_BANANI_211961933

import Enums.Category;
import Managers.ManagerFacade;
import Models.*;

import java.util.*;

public class Main {
    private static Scanner sc;
    private static ManagerFacade managerFacade;
    private static String input;
    private static String message;
    private static Factory factory;

    private static UserInput uI; // TRY
    private static Menu menu; // TRY

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        uI = new UserInput();
        menu = new Menu();
        managerFacade = ManagerFacade.getInstance();
        factory = new Factory();
        menu(managerFacade);
        sc.close();
    }

    private static void menu(ManagerFacade managerFacade) {
        menu.start();
        int choice;
        do {
            menu.printMenu();
            choice = uI.getInt();
            switch (choice) {
                case -1:
                    break;
                case 0:
                    System.out.println("\nThanks for using our system. GoodBye!");
                    break;
                case 1:
                    managerFacade.case1(uI);
                    break;
                case 2:
                    managerFacade.case2(uI);
                    break;
                case 3:
                    managerFacade.case3(uI);
                    break;
                case 4:
                    managerFacade.case4(uI);
                    break;
                case 5:
                    managerFacade.case5(uI);
                    break;
                case 6:
                    System.out.println(managerFacade.buyersInfo());
                    break;
                case 7:
                    System.out.println(managerFacade.sellersInfo());
                    break;
                case 8:
                    System.out.println(managerFacade.productsByCategory());
                    break;
                case 9:
                    case9();
                    break;
                case 10:
                    case10();
                    break;
                case 99:
                    case99();
                    break;
                case 100:
                    case100();
                    break;
                case 101:
                    case101();
                    break;
                case 102:
                    case102();
                    break;
                case 103:
                    case103();
                    break;
                default:
                    System.out.println("\nPlease enter a valid choice in range 0-10 / 99 - 103!");
                    break;
            }
        } while (choice != 0);
    }



    public static void case9 () {
        if (managerFacade.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (managerFacade.getBuyers()[buyerIndex].getHistoryCartsNum() == 0) {
            System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
            return;
        }
        System.out.println(managerFacade.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        System.out.println("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
        do {
             input = sc.next();
             if (input.equals("-1")) return;
             message = managerFacade.isValidHistoryCartIndex(input, buyerIndex);
             if (message != null) {
                 System.out.println(message);
             }
        } while (message != null);
        int historyCartIndex = Integer.parseInt(input);
        managerFacade.replaceCarts(historyCartIndex - 1, buyerIndex);
        System.out.println("Your current cart update successfully.");
    }

    public static void case10(){
        int sellerIndex = managerFacade.getNumberOfSellers();
        int buyersIndex = managerFacade.getNumberOfBuyers();
        factory.hardcodedFactory(managerFacade, sellerIndex, buyersIndex);
    }

    public static void case99() {
        managerFacade.printProductsName();
    }

    public static void case100(){
        if(managerFacade.getNumberOfProducts() != 0){
          Map<String,Integer> map = managerFacade.productsToLinkedMap();                                     // first loop for create
          map.forEach((key, value) -> System.out.println(key + ".........." + value));         // second loop for print
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");

}

    public static void case101() {
        if (managerFacade.getNumberOfProducts() != 0) {
            Map<String, Integer> map = managerFacade.productsToLinkedMap();
            System.out.println("Please enter a string: (Enter -1 to return main menu)");
            input = sc.nextLine().toLowerCase();
            if (input.equals("-1")) return;
            System.out.printf("the number of times that " + input + " appears in the OG ARRAY is %d\n" , map.get(input) == null ? 0 : map.get(input));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public static void case102(){
        if(managerFacade.getNumberOfProducts() != 0) {
            List<String> setList = new ArrayList<>(managerFacade.productsNameToLinkedSet());
            List<String> doubleNames = new ArrayList<>();
            ListIterator<String> iterator = setList.listIterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                doubleNames.add(key);
                doubleNames.add(key);
            }
            ListIterator<String> doubleIterator = doubleNames.listIterator(doubleNames.size());
            while(doubleIterator.hasPrevious()){
                System.out.println(doubleIterator.previous());
            }
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public static void case103() {
        if (managerFacade.getNumberOfProducts() != 0) {
            Set<?> productsSet = managerFacade.productsToTree();
            Iterator<?> productsIterator = productsSet.iterator();
            while (productsIterator.hasNext()) {
                System.out.println(productsIterator.next().toString().toUpperCase());
            }
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    /*
    public static int chooseSeller () {
        System.out.println(managerFacade.sellersNames());
        System.out.println("Please choose seller from the list above: (Enter -1 to return main menu)");
        while (true) {
            input = sc.next();
            if (input.equals("-1")) return -1;
            message = managerFacade.chooseValidSeller(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }
     */


    public static int chooseBuyer () {
        System.out.println(managerFacade.buyersNames());
        System.out.println("Please choose buyer from the list above: (Enter -1 to return main menu)");
        while (true) {
            input = sc.next();
            if (input.equals("-1")) return -1;
            message = managerFacade.chooseValidBuyer(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }

}

