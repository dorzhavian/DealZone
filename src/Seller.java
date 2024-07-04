
import java.util.Arrays;

public class Seller extends User {
    private static final int SIZE_INCREARSE = 2;
    private Product[] products;
    private int numOfProducts; //contains the num of products in the array

    public Seller(String userName, String password) {
        super(userName, password);
        products = new Product[0];
        numOfProducts = 0;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public String getUserName() {
        return userName;
    }

    public Product[] getProducts() {
        return products;
    }

    public void addProduct (Product p1) {     // FIX! - make like addProductToBuyer?
        if (products.length == numOfProducts) {
            if (products.length == 0) {
                products = Arrays.copyOf(products, 1);
            }
            else {
                products = Arrays.copyOf(products, products.length * SIZE_INCREARSE);
            }
        }
        products[numOfProducts++] = p1;
        System.out.println("Product added successfully.");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (numOfProducts == 0) {
            sb.append("No products yet for this seller.");
            return sb.toString();
        }
        sb.append("Seller products: \n");
        for (int i = 0; i < numOfProducts; i++) {
            sb.append(i+1).append(") ").append(products[i].toString()).append('\n');
        }
        return sb.toString();
    }
}

