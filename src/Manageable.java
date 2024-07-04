public interface Manageable {
    void addSeller(String username, String password);

    void addBuyer(String username, String password, String address);

    boolean isExist(String username, String type);

    void printSellersInfo();

    void printBuyersInfo();

    void printByCategory();

    void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex, boolean specialPackage);

    void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackadgePrice);

    void pay(int buyerIndex);

    void addToCategoryArray(Product p);

    void replaceCarts (int historyCartIndex, int buyerIndex);

}
