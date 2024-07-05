public interface Manageable {
    void addSeller(String username, String password) throws AlreadyExistException;

    void addBuyer(String username, String password, String address) throws AlreadyExistException;

    void printSellersInfo();

    void printBuyersInfo();

    void printByCategory();

    void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex, boolean specialPackage);

    void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackadgePrice);

    void pay(int buyerIndex);

    void addToCategoryArray(Product p);

    void replaceCarts (int historyCartIndex, int buyerIndex);

}
