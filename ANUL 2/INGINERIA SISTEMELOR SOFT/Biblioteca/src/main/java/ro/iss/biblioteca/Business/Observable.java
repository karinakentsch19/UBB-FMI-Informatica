package ro.iss.biblioteca.Business;

import java.util.List;

public interface Observable {
    public void addObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void notifyAllObservers();
}
