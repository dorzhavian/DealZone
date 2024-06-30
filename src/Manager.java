import java.util.Arrays;

public class Manager {
    private final int SIZE_INCREASE = 2;
    private Seller[] sellers;
    private int numberOfSellers;
    private Buyer[] buyers;
    private int numberOfBuyers;

    public Manager() {
        buyers = new Buyer[0];
        sellers = new Seller[0];
    }

    public Seller[] getSellers() {
        return sellers;
    }

    public int getNumberOfSellers() {
        return numberOfSellers;
    }

    public Buyer[] getBuyers() {
        return buyers;
    }

    public int getNumberOfBuyers() {
        return numberOfBuyers;
    }

    public void addSeller(String username, String password) {
        Seller seller = new Seller(username, password);
        if (sellers.length == numberOfSellers) {
            if (sellers.length == 0) {
                sellers = Arrays.copyOf(sellers,1);
            }
            sellers = Arrays.copyOf(sellers, sellers.length * SIZE_INCREASE);
        }
        sellers[numberOfSellers++] = seller;
        System.out.println("Seller added successfully.");
    }

    public void addBuyer(String username, String password, String address) {
        Buyer buyer = new Buyer(username, password, address);
        if (buyers.length == numberOfBuyers) {
            if (buyers.length == 0) {
                buyers = Arrays.copyOf(buyers,1);
            }
            buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
        }
        buyers[numberOfBuyers++] = buyer;
        System.out.println("Buyer added successfully.");
    }

    public boolean isExist(String username, String type) {
        if (type.equals("seller")) {
            if (numberOfSellers == 0){
                return true;
            }
            for (int i = 0; i < numberOfSellers ; i++) {
                if (sellers[i].getUserName().equalsIgnoreCase(username)) {
                    System.out.println("Username already exist, please enter different username: ");
                    return false;
                }
            }
            return true;
        } else if (type.equals("buyer")) {
            if (numberOfBuyers == 0){
                return true;
            }
            for (int i = 0; i < numberOfBuyers ; i++) {
                if (buyers[i].getUserName().equalsIgnoreCase(username)) {
                    System.out.println("Username already exist, please enter different username: ");
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void printSellersInfo() {
        if (numberOfSellers == 0) {
            System.out.println("Haven't sellers yet. return to main menu");
            return;
        }
        for (int i = 0; i < numberOfSellers; i++) {
            System.out.println(i + 1 + ") " + sellers[i].getUserName() + ":");
            System.out.println(sellers[i].toString());
        }
    }

    public void printBuyersInfo() {
        if (numberOfBuyers == 0) {
            System.out.println("Haven't buyers yet. return to main menu");
            return;
        }
        for (int i = 0; i < numberOfBuyers; i++) {
            System.out.print(i + 1 + ") ");
            System.out.println(buyers[i].toString());
        }
    }

    public void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex) {
        Product p1 = new Product(sellers[sellerIndex].getProducts()[productIndex]);
        buyers[buyerIndex].getCurrentCart().addProductToCart(p1);
    }

    public void addProductSeller(int sellerIndex, String productName, double productPrice) {
        Product p1 = new Product(productName, productPrice);
        sellers[sellerIndex].addProduct(p1);
    }

    public void pay(int buyerIndex) {
        buyers[buyerIndex].payAndMakeHistoryCart();
    }
}


