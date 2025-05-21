package com.example.comenzirestaurant.Utils;

public interface Observer {
    public void update(OrderStatus status, Integer tableId);
}
