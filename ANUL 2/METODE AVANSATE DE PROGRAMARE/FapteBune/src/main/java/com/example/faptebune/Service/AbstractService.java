package com.example.faptebune.Service;

import com.example.faptebune.Utils.Observable;
import com.example.faptebune.Utils.Observer;

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
    public void notifyAllObservers() {
        observerList.forEach(Observer::update);
    }
}
