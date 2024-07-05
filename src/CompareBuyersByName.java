import java.util.Comparator;

public class CompareBuyersByName implements Comparator<Buyer> {
    @Override
    public int compare(Buyer b1, Buyer b2) {
        if (b1 == null || b2 == null) return -1;
        return b1.getUserName().compareTo(b2.getUserName());
    }
}
