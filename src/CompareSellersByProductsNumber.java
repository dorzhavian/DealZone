import java.util.Comparator;

public class CompareSellersByProductsNumber implements Comparator<Seller> {
    @Override
    public int compare(Seller s1, Seller s2) {
        return s1.getNumOfProducts() - s2.getNumOfProducts();
    }
}
