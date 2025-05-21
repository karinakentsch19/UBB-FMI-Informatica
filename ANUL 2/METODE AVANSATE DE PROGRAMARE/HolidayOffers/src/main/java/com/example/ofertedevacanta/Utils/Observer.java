package com.example.ofertedevacanta.Utils;

import com.example.ofertedevacanta.Domain.Client;

import java.util.List;

public interface Observer {
    public void update(Hobby hobby, String hotelName, List<Client> clients);
}
