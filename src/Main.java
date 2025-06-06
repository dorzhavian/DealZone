//DOR_ZHAVIAN_211337845_&_ASAF_BANANI_211961933

import Managers.ManagerFacade;
import Commands.*;
import Models.*;

public class Main {

    public static void main(String[] args) {
        ManagerFacade managerFacade = ManagerFacade.getInstance();
        Command c99 = new Command99(managerFacade);
        Command c100 = new Command100(managerFacade);
        Command c101 = new Command101(managerFacade);
        Command c102 = new Command102(managerFacade);
        Command c103 = new Command103(managerFacade);
        int choice;
        Menu.start();
        do {
            Menu.printMenu();
            choice = UserInput.getInt("Please enter your choice: ");
            switch (choice) {
                case -1:
                    break;
                case 0:
                    System.out.println("\nThanks for using our system. GoodBye!");
                    break;
                case 1:
                    managerFacade.case1();
                    break;
                case 2:
                    managerFacade.case2();
                    break;
                case 3:
                    managerFacade.case3();
                    break;
                case 4:
                    managerFacade.case4();
                    break;
                case 5:
                    managerFacade.case5();
                    break;
                case 6:
                    managerFacade.case6();
                    break;
                case 7:
                    managerFacade.case7();
                    break;
                case 8:
                    managerFacade.case8();
                    break;
                case 9:
                    managerFacade.case9();
                    break;
                case 10:
                    managerFacade.hardcoded();
                    break;
                case 99:
                    c99.execute();
                    break;
                case 100:
                    c100.execute();
                    break;
                case 101:
                    c101.execute();
                    break;
                case 102:
                    c102.execute();
                    break;
                case 103:
                    c103.execute();
                    break;
                case 104:
                    managerFacade.case104();
                    break;
                case 105:
                    managerFacade.case105();
                    break;
                default:
                    System.out.println("\nPlease enter a valid choice in range 0-10 / 99 - 103!");
                    break;
            }
        } while (choice != 0);
        managerFacade.closeConnection();
        UserInput.close();
    }
}

