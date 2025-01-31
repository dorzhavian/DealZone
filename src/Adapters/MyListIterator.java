package Adapters;

public interface MyListIterator<E> {
    boolean myHasNext();
    boolean myHasPrevious();
    E next();
    E previous();
}

