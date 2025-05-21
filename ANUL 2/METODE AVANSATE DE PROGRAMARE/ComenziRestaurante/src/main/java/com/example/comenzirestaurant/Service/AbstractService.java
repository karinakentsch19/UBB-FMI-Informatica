package com.example.comenzirestaurant.Service;

import com.example.comenzirestaurant.Utils.Observable;
import com.example.comenzirestaurant.Utils.Observer;
import com.example.comenzirestaurant.Utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class AbstractService implements Observable {

    List<Observer> observerList = new ArrayList<>();
    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyAllObservers(OrderStatus status, Integer tableId) {
        observerList.forEach(o -> o.update(status, tableId));
    }
}
