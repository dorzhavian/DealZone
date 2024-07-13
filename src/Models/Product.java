package Models;

import Enums.Category;

public class Product {
    private final String productName;
    private final double productPrice;
    protected static int idGenerator;
    protected int id;
    private final Category category;

    public Product(String productName, double productPrice, Category category) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.id = ++idGenerator;
        this.category = category;
    }

    public Product(Product other) {
        this.productName = other.productName;
        this.productPrice = other.productPrice;
        this.id = other.id;
        this.category = other.category;
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
        return sb.toString();
    }
}
