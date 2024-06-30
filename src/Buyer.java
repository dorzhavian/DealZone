import java.util.Arrays;

public class Buyer {

    private static final int SIZE_INCREASE = 2;
    private final String userName;
    private final String password;
    private final String address;
    private Cart currentCart;
    private Cart[] historyCart;
    private int historyCartsNum;

    public Buyer(String userName, String password, String address) {
        this.userName = userName;
        this.password = password;
        this.address = address;
        currentCart = new Cart();
        historyCart = new Cart[0];
    }

    public String getUserName() {
        return userName;
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
        System.out.println(" ____   _ __   ____  __ _____ _   _ _____                               \n" +
                "|  _ \\ / \\\\ \\ / /  \\/  | ____| \\ | |_   _|                              \n" +
                "| |_) / _ \\\\ V /| |\\/| |  _| |  \\| | | |                                \n" +
                "|  __/ ___ \\| | | |  | | |___| |\\  | | |                                \n" +
                "|_| /_/   \\_\\_|_|_|_ |_|_____|_|_\\_|_|_|  _____ ____  _____ _   _ _     \n" +
                "            / ___|| | | |/ ___/ ___/ ___|| ____/ ___||  ___| | | | |    \n" +
                "            \\___ \\| | | | |   \\___ \\___ \\|  _| \\___ \\| |_  | | | | |    \n" +
                "             ___) | |_| | |___ ___) |__) | |___ ___) |  _| | |_| | |___ \n" +
                "            |____/ \\___/ \\____|____/____/|_____|____/|_|    \\___/|_____|");
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


