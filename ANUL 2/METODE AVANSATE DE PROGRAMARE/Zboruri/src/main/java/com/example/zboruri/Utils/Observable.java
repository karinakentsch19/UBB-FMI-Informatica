package com.example.zboruri.Utils;

public interface Observable {
    public void addObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void notifyAllObservers(Long flightId);
}
