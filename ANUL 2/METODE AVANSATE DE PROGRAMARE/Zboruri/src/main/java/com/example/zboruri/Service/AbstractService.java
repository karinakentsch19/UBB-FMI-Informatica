package com.example.zboruri.Service;

import com.example.zboruri.Utils.Observable;
import com.example.zboruri.Utils.Observer;

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
    public void notifyAllObservers(Long flightId) {
        observerList.forEach(observer -> observer.update(flightId));
    }
}
