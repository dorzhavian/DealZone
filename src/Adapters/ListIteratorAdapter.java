package Adapters;
import java.util.ListIterator;

public class ListIteratorAdapter<E> implements MyListIterator<E> {
    private final ListIterator<E> listIterator;

    public ListIteratorAdapter(ListIterator<E> listIterator) {
        this.listIterator = listIterator;
    }

    @Override
    public boolean myHasNext() {
        return listIterator.hasNext();
    }

    @Override
    public boolean myHasPrevious() {
        return listIterator.hasPrevious();
    }

    @Override
    public E next() {
        return listIterator.next();
    }

    @Override
    public E previous() {
        return listIterator.previous();
    }
}

