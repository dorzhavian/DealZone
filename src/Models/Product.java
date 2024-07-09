package Models;

import Exceptions.EmptyException;
import Exceptions.OnlyNumbersUserNameException;
import Exceptions.NegativeOrZeroPriceException;

public class Product {
    private final String productName;
    private final double productPrice;
    private final double specialPackagePrice;
    private static int idGenerator;
    private final int id;
    private final Category category;
    private String specialPackageBuyerChoice;

    public Product(String productName, double productPrice, Category category, double specialPackagePrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.id = ++idGenerator;
        this.category = category;
        this.specialPackagePrice = specialPackagePrice;
        this.specialPackageBuyerChoice = " (WITHOUT) ";
    }

    public Product(Product other) {
        this.productName = other.productName;
        this.productPrice = other.productPrice;
        this.id = other.id;
        this.category = other.category;
        this.specialPackagePrice = other.specialPackagePrice;
        this.specialPackageBuyerChoice = other.specialPackageBuyerChoice;
    }

    public void setSpecialPackageBuyerChoice(String specialPackageBuyerChoice) {
        this.specialPackageBuyerChoice = specialPackageBuyerChoice;
    }

    public String getSpecialPackageBuyerChoice() {
        return specialPackageBuyerChoice;
    }

    public double getSpecialPackagePrice() {
        return specialPackagePrice;
    }

    public Category getCategory() {
        return category;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public static void isValidProductName (String inputProductName) throws EmptyException, OnlyNumbersUserNameException {
        if (inputProductName == null || inputProductName.trim().isEmpty()) throw new EmptyException("Models.Product name");
        if (Manager.isNumeric(inputProductName)) throw new OnlyNumbersUserNameException("Models.Product");
    }

    public static double isValidPrice (String inputPrice) throws NegativeOrZeroPriceException {
        double productPrice = Double.parseDouble(inputPrice);
        if (productPrice <= 0) throw new NegativeOrZeroPriceException();
        return productPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Models.Product name: ").append(productName)
                .append(", Models.Product price: ").append(productPrice)
                .append(", Models.Product id: ").append(id)
                .append(", Models.Product category: ").append(category);
        if (specialPackagePrice != 0) {
            sb.append(", Models.Product special package: ").append(specialPackagePrice);
        }
        return sb.toString();
    }
}
