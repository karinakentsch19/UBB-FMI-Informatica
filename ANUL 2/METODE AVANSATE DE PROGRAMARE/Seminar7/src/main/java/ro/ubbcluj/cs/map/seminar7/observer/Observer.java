package ro.ubbcluj.cs.map.seminar7.observer;

public interface Observer<E extends Event> {
    void update(E evemt);
}
