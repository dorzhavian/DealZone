import Exceptions.AlreadyExistException;
import Exceptions.IndexOutOfRangeException;

public interface Manageable {
    void addSeller(String username, String password) throws AlreadyExistException;

    void addBuyer(String username, String password, String address) throws AlreadyExistException;

    void printSellersInfo();

    void printBuyersInfo();

    void printByCategory();

    void printSellersNames ();

    void printBuyersNames ();

    void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex, boolean specialPackage);

    void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackadgePrice);

    void pay(int buyerIndex);

    void addToCategoryArray(Product p);

    void replaceCarts (int historyCartIndex, int buyerIndex);

    boolean validName (String name, int whichCase);

    boolean validPass (String pass);

    boolean isValidAddress(String address);

    boolean isEmptySellers();

    boolean isEmptyBuyers();

    void isInRangeSellers (String index) throws IndexOutOfRangeException;

    void isInRangeBuyers (String index) throws IndexOutOfRangeException;

    boolean chooseValidSeller(String indexInput);

    boolean chooseValidBuyer(String indexInput);
    }
