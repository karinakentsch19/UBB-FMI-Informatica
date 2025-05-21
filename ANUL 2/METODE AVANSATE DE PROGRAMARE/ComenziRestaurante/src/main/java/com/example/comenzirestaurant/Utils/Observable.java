package com.example.comenzirestaurant.Utils;

import java.util.List;

public interface Observable {
    public void addObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void notifyAllObservers(OrderStatus status, Integer tableId);
}
