package com.example.faptebune.Utils;

public interface Observable {
    public void addObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void notifyAllObservers();
}
