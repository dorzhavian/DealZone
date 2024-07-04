public interface Manageable {
    public void addSeller(String username, String password);

    public void addBuyer(String username, String password, String address);

    public boolean isExist(String username, String type);

    public void printSellersInfo();

    public void printBuyersInfo();

    public void printByCategory();

    public void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex, boolean specialPackage);

    public void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackadgePrice);

    public void pay(int buyerIndex);

    public void addToCategoryArray(Product p);

    public void replaceCarts (int historyCartIndex, int buyerIndex);

}
