import Exceptions.EmptyException;
import Exceptions.YesNoChoiceException;

import java.util.Arrays;

public class Buyer extends User {

    private static final int SIZE_INCREASE = 2;
    private final String address;
    private Cart currentCart;
    private Cart[] historyCart;
    private int historyCartsNum;

    public Buyer(String userName, String password, String address) {
        super(userName, password);
        this.address = address;
        currentCart = new Cart();
        historyCart = new Cart[0];
    }

    public static void isValidAddress(String inputAddress) throws EmptyException {
        if (inputAddress == null || inputAddress.trim().isEmpty()) throw new EmptyException("Address");
    }

    public static void yesOrNoChoiceForBuyer(String inputSpecialPackage) throws EmptyException, YesNoChoiceException {
        if (inputSpecialPackage == null || inputSpecialPackage.trim().isEmpty()) throw new EmptyException("Choice");
        if (!(inputSpecialPackage.equalsIgnoreCase("yes") || inputSpecialPackage.equalsIgnoreCase("no"))) throw new YesNoChoiceException();
    }

    public Cart[] getHistoryCart() {
        return historyCart;
    }

    public void setCurrentCart(Cart currentCart) {
        this.currentCart = currentCart;
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public void payAndMakeHistoryCart(){
        Cart hCart = new Cart(currentCart);
        if (historyCart.length == historyCartsNum) {
            if (historyCart.length == 0) {
                historyCart = Arrays.copyOf(historyCart, 1);
            }
            else {
                historyCart = Arrays.copyOf(historyCart, historyCart.length * SIZE_INCREASE);
            }
        }
        historyCart[historyCartsNum++] = hCart;
        currentCart = new Cart();
        System.out.println("----------------------------------------------------------------");
        System.out.println("""
                 ____   _ __   ____  __ _____ _   _ _____                              \s
                |  _ \\ / \\\\ \\ / /  \\/  | ____| \\ | |_   _|                             \s
                | |_) / _ \\\\ V /| |\\/| |  _| |  \\| | | |                               \s
                |  __/ ___ \\| | | |  | | |___| |\\  | | |                               \s
                |_| /_/   \\_\\_|_|_|_ |_|_____|_|_\\_|_|_|  _____ ____  _____ _   _ _    \s
                            / ___|| | | |/ ___/ ___/ ___|| ____/ ___||  ___| | | | |   \s
                            \\___ \\| | | | |   \\___ \\___ \\|  _| \\___ \\| |_  | | | | |   \s
                             ___) | |_| | |___ ___) |__) | |___ ___) |  _| | |_| | |___\s
                            |____/ \\___/ \\____|____/____/|_____|____/|_|    \\___/|_____|""");
        System.out.println("----------------------------------------------------------------");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Buyer details:\n").append("   Name: ").append(userName).append("\n")
                .append("   Address: ").append(address).append("\n\n");
        if (currentCart.getNumOfProducts() == 0) {
            sb.append("Cart is empty\n");
        } else {
            sb.append(currentCart.toString());
        }
        if (historyCartsNum == 0) {
            return sb.append("No history carts.\n").toString();
        }
        sb.append("\nHistory carts details : \n");
        for (int i = 0; i < historyCartsNum; i++) {
            sb.append(i+1).append(") ").append(historyCart[i].toString())
                    .append(historyCart[i].getDate()).append("\n\n");
        }
        return sb.toString();
    }
}


