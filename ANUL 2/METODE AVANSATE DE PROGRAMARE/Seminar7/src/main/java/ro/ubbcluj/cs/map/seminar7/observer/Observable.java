package ro.ubbcluj.cs.map.seminar7.observer;

public interface Observable <E extends Event>{
    void notifyAll(E e);
    void addObserver(Observer<E> observer);

    void removeObserver(Observer<E> observer);
}
