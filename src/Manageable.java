import Exceptions.AlreadyExistException;
import Exceptions.EmptyUsersArrayException;
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

    void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice);

    void pay(int buyerIndex);

    void addToCategoryArray(Product p);

    void replaceCarts (int historyCartIndex, int buyerIndex);

    boolean validName (String name, int whichCase);

    boolean validPass (String pass);

    int validCategory (String categoryInput);

    boolean isValidAddress(String address);

    boolean isEmptySellers();

    boolean isEmptyBuyers();

    int isInRangeSellers (String index) throws IndexOutOfRangeException;

    int isInRangeBuyers (String index) throws IndexOutOfRangeException;

    int chooseValidSeller(String indexInput);

    int chooseValidBuyer(String indexInput);

    void isValidNumOfSellers() throws EmptyUsersArrayException;

    void isValidNumOfBuyers() throws EmptyUsersArrayException;

    boolean validProductName(String productNameInput);

    double validPrice (String productPriceInput);

    boolean haveProductToSell (int indexSeller);

    int validProductIndex(int sellerIndex, String productIndex);

    boolean buyerYesOrNoChoice(String input);

    void printBuyerCurrentCart(int buyerIndex);
    }
