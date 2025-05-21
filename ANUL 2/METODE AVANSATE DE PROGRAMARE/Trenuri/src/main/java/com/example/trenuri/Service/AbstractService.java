package com.example.trenuri.Service;

import com.example.trenuri.Utils.Observable;
import com.example.trenuri.Utils.Observer;

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
    public void notifyAllObservers(Integer departureCityId, Integer destinationCityId) {
        observerList.forEach(observer -> observer.update(departureCityId, destinationCityId));
    }

    public Integer countHowManyWindowsHaveThisFilter(String departureCity, String destinationCity){
        Integer cont = 0;
        for (Observer observer: observerList)
            if (observer.hasFilters(departureCity,destinationCity))
                cont++;
        return cont;
    }
}
