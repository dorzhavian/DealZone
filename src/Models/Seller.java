package Models;

import Exceptions.IndexOutOfRangeException;

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

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public Product[] getProducts() {
        return products;
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
        System.out.println("Models.Product added successfully.");
    }

    public static int validProductOfSeller (String input, int thisSellerNumOfProducts) throws IndexOutOfRangeException {
        int productIndex = Integer.parseInt(input);
        if (productIndex <= 0 || productIndex > thisSellerNumOfProducts) throw new IndexOutOfRangeException("Models.Product");
        return productIndex;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (numOfProducts == 0) {
            sb.append("No products yet.\n");
            return sb.toString();
        }
        sb.append("\nModels.Seller products: \n");
        for (int i = 0; i < numOfProducts; i++) {
            sb.append(i+1).append(") ").append(products[i].toString()).append('\n');
        }
        return sb.toString();
    }
}

