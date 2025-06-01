package Models;

import java.util.Arrays;

public class Seller extends User {
    private static final int SIZE_INCREASE = 2;
    private Product[] products;
    private int numOfProducts; //contains the num of products in the array

    public Seller(String userName, String password) {
        super(userName, password);
        products = new Product[0];
        numOfProducts = 0;
    }

    public Seller(int id, String userName, String password) {
        super(id, userName, password);
        products = new Product[numOfProducts];
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public Product[] getProducts() {
        return products;
    }

    public int productIndexInSellerArr(int productID)
    {
        int i;
        for (i = 0; i < numOfProducts; i++) {
            if(products[i].getId() == productID)
                break;
        }
        return i;
    }

    public void addProduct (Product p1) {
        if (products.length == numOfProducts) {
            if (products.length == 0) {
                products = Arrays.copyOf(products, 1);
            }
            else {
                products = Arrays.copyOf(products, products.length * SIZE_INCREASE);
            }
        }
        products[numOfProducts++] = p1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (numOfProducts == 0) {
            sb.append("\nNo products yet.\n");
            return sb.toString();
        }
        sb.append("\nSeller products: \n");
        for (int i = 0; i < numOfProducts; i++) {
            sb.append("   ").append(i+1).append(") ").append(products[i].toString()).append('\n');
        }
        return sb.toString();
    }
}

