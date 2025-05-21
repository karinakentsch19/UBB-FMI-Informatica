package com.example.trenuri.Utils;

public interface Observer {
    public void update(Integer departureCityId, Integer destinationCityId);

    public boolean hasFilters(String departureCity, String destinationCity);
}
