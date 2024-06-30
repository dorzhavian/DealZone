public class Product {
    private String productName;
    private double productPrice;
    private final static int SIZE_INCREASE = 2;

    public Product(String productName, double productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Product(Product other) {
        this.productName = other.productName;
        this.productPrice = other.productPrice;
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
        sb.append("Product name: ").append(getProductName())
                .append(", Product price: ").append(getProductPrice());
        return sb.toString();
    }
}
