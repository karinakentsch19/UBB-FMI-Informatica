package com.example.ofertedevacanta.Service;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Utils.Hobby;
import com.example.ofertedevacanta.Utils.Observable;
import com.example.ofertedevacanta.Utils.Observer;

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
    public void notifyAllObservers(Hobby hobby, String hotelName, List<Client> clients) {
        observerList.forEach(o -> o.update(hobby, hotelName, clients));
    }
}
