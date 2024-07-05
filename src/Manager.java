import java.util.Arrays;
import java.util.Comparator;

public class Manager implements Manageable {
    private final int SIZE_INCREASE = 2;
    private Seller[] sellers;
    private int numberOfSellers;
    private Buyer[] buyers;
    private int numberOfBuyers;
    private Categories categoriesArrays;
    private Comparator <Seller> comparatorSeller;
    private Comparator <Buyer> comparatorBuyer;

    public Manager() {
        buyers = new Buyer[0];
        sellers = new Seller[0];
        categoriesArrays = new Categories();
        comparatorSeller = new CompareSellersByProductsNumber();
        comparatorBuyer = new CompareBuyersByName();
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
                sellers = Arrays.copyOf(sellers, 1);
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
                buyers = Arrays.copyOf(buyers, 1);
            }
            buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
        }
        buyers[numberOfBuyers++] = buyer;
        System.out.println("Buyer added successfully.");
    }

    public boolean isExists(String username, String type) {
        if (type.equals("seller")) {
            if (User.isExist(sellers, username, numberOfSellers)) {
                System.out.println("Seller already exists. Please enter a different name.");
                return true;
            }
        } else if (type.equals("buyer")) {
            if (User.isExist(buyers, username, numberOfBuyers)) {
                System.out.println("Buyer already exists. Please enter a different name.");
                return true;
            }
        }
        return false;
    }


    public void printSellersInfo() {
        if (numberOfSellers == 0) {
            System.out.println("Haven't sellers yet. return to main menu");
            return;
        }
        Arrays.sort(sellers, 0, numberOfSellers, comparatorSeller);
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
        Arrays.sort(buyers, 0, numberOfBuyers, comparatorBuyer);
        for (int i = 0; i < numberOfBuyers; i++) {
            System.out.print(i + 1 + ") ");
            System.out.println(buyers[i].toString());
        }
    }

    public void printByCategory() {
        if (numberOfSellers == 0) {
            System.out.println("Haven't sellers yet. return to main menu");
            return;
        }
        System.out.println(categoriesArrays.toString());
    }

    public void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex, boolean specialPackage) {
        Product p1 = new Product(sellers[sellerIndex].getProducts()[productIndex]);
        buyers[buyerIndex].getCurrentCart().addProductToCart(p1, specialPackage);
    }

    public void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackadgePrice) {
        Product p1 = new Product(productName, productPrice, c, specialPackadgePrice);
        sellers[sellerIndex].addProduct(p1);
        addToCategoryArray(p1);
    }

    public void pay(int buyerIndex) {
        buyers[buyerIndex].payAndMakeHistoryCart();
    }

    public void addToCategoryArray(Product p) {
        switch (p.getCategory()) {
            case ELECTRONIC:
                categoriesArrays.addElectronic(p);
                break;
            case CHILDREN:
                categoriesArrays.addChild(p);
                break;
            case CLOTHES:
                categoriesArrays.addClothes(p);
                break;
            case OFFICE:
                categoriesArrays.addOffice(p);
                break;
            default:
                break;
        }
    }

    public void replaceCarts(int historyCartIndex, int buyerIndex) {
        buyers[buyerIndex].setCurrentCart(buyers[buyerIndex].getHistoryCart()[historyCartIndex]);
    }

    public static boolean isNumeric(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}


