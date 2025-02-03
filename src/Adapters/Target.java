package Adapters;

public interface Target<E> {
    boolean myHasNext();
    boolean myHasPrevious();
    E next();
    E previous();
}

