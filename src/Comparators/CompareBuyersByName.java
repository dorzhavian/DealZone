package Comparators;

import Models.Buyer;

import java.util.Comparator;

public class CompareBuyersByName implements Comparator<Buyer> {
    @Override
    public int compare(Buyer b1, Buyer b2) {
        return b1.getUserName().toLowerCase().compareTo(b2.getUserName().toLowerCase());
    }
}
