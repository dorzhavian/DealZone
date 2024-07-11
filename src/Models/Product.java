package Models;

import Enums.Category;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product name: ").append(productName)
                .append(", Product price: ").append(productPrice)
                .append(", Product id: ").append(id)
                .append(", Product category: ").append(category);
        if (specialPackagePrice != 0) {
            sb.append(", Product have a special package, additional price is: ").append(specialPackagePrice);
        }
        return sb.toString();
    }
}
