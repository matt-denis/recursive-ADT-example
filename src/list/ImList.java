package list;

import java.util.Objects;

public interface ImList<E> {
    
    public static<E> ImList<E> empty() {
        return new Empty<>();
    }

    ImList<E> cons(E e);

    E first();

    ImList<E> rest();

    int size();
}

class Empty<E> implements ImList<E> {

    public ImList<E> cons(E e) {
        return new Cons<>(e, this);
    }

    public E first() {
        throw new UnsupportedOperationException();
    }

    public ImList<E> rest() {
        throw new UnsupportedOperationException();
    }

    public int size() { return 0; }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Empty);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

class Cons<E> implements ImList<E> {

    private final E e;
    private final ImList<E> rest;
    private int size;

    public Cons(E e, ImList<E> rest) {
        this.e = e;
        this.rest = rest;
    }

    public ImList<E> cons(E e) {
        return new Cons<>(e, this);
    }

    public E first() { return e; }

    public ImList<E> rest() { return rest; }

    public int size() {
        if (size == 0) size = 1 + size();
        return size;
    }

    @Override 
    public String toString() {
        return e.toString() + " " + rest.toString();
    }

    @Override 
    public boolean equals(Object o) {
        if (!(o instanceof Cons)) return false;
        Cons<?> that = (Cons<?>) o;
        return this.e.equals(that.e) && rest.equals(that.rest);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(e) + 37 * rest.hashCode();
    }

}
