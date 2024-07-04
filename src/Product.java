public class Product {
    private String productName;
    private double productPrice;
    private double specialPackadgePrice;
    private static int idGenerator;
    private int id;
    private Category category;
    private String specialPackageBuyerChoice;

    public Product(String productName, double productPrice, Category category, double specialPackadgePrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.id = ++idGenerator;
        this.category = category;
        this.specialPackadgePrice = specialPackadgePrice;
        this.specialPackageBuyerChoice = " (NO) ";
    }

    public Product(Product other) {
        this.productName = other.productName;
        this.productPrice = other.productPrice;
        this.id = other.id;
        this.category = other.category;
        this.specialPackadgePrice = other.specialPackadgePrice;
        this.specialPackageBuyerChoice = other.specialPackageBuyerChoice;
    }

    public void setSpecialPackageBuyerChoice(String specialPackageBuyerChoice) {
        this.specialPackageBuyerChoice = specialPackageBuyerChoice;
    }

    public String getSpecialPackageBuyerChoice() {
        return specialPackageBuyerChoice;
    }

    public double getSpecialPackadgePrice() {
        return specialPackadgePrice;
    }

    public Category getCategory() {
        return category;
    }

    public String getProductName() {
        return productName;
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
        if (specialPackadgePrice != 0) {
            sb.append(", Product special package: ").append(specialPackadgePrice);
        }
        return sb.toString();
    }
}
