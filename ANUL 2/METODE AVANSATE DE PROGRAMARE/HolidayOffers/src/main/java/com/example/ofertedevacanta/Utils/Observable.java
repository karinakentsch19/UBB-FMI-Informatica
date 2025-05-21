package com.example.ofertedevacanta.Utils;

import com.example.ofertedevacanta.Domain.Client;

import java.util.List;

public interface Observable {
    public void addObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void notifyAllObservers(Hobby hobby, String hotelName, List<Client> clients);
}
