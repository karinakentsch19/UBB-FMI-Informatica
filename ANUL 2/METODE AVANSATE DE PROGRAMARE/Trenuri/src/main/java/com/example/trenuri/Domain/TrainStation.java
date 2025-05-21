package com.example.trenuri.Domain;

import java.util.Objects;

public class TrainStation extends Entity<Integer>{

    private Integer departureCityId;

    private Integer destinationCityId;


    public TrainStation(Integer departureCityId, Integer destinationCityId) {
        this.departureCityId = departureCityId;
        this.destinationCityId = destinationCityId;
    }

    public Integer getDepartureCityId() {
        return departureCityId;
    }

    public void setDepartureCityId(Integer departureCityId) {
        this.departureCityId = departureCityId;
    }

    public Integer getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(Integer destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainStation that = (TrainStation) o;
        return Objects.equals(departureCityId, that.departureCityId) && Objects.equals(destinationCityId, that.destinationCityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureCityId, destinationCityId);
    }

    @Override
    public String toString() {
        return "TrainStation{" +
                "departureCityId=" + departureCityId +
                ", destinationCityId=" + destinationCityId +
                '}';
    }
}
