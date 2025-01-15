//DOR_ZHAVIAN_211337845_&_ASAF_BANANI_211961933

import Managers.ManagerFacade;
import Models.*;

public class Main {

    public static void main(String[] args) {
        InputHandler uI = UserInput.getInstance();           // SINGLETON NECESSARY????????
        ManagerFacade managerFacade = ManagerFacade.getInstance();
        int choice;
        Menu.start();
        do {
            Menu.printMenu();
            choice = uI.getInt("Please enter your choice: ");
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
                    managerFacade.case6();
                    break;
                case 7:
                    managerFacade.case7();
                    break;
                case 8:
                    managerFacade.case8();
                    break;
                case 9:
                    managerFacade.case9(uI);
                    break;
                case 10:
                    managerFacade.hardcoded();
                    break;
                case 99:
                    managerFacade.case99();
                    break;
                case 100:
                    managerFacade.case100();
                    break;
                case 101:
                    managerFacade.case101(uI);
                    break;
                case 102:
                    managerFacade.case102();
                    break;
                case 103:
                    managerFacade.case103();
                    break;
                default:
                    System.out.println("\nPlease enter a valid choice in range 0-10 / 99 - 103!");
                    break;
            }
        } while (choice != 0);
        uI.close();
    }
}

