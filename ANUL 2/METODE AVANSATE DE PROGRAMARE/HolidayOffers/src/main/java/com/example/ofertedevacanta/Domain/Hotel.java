package com.example.ofertedevacanta.Domain;

import com.example.ofertedevacanta.Utils.Type;

import java.util.Objects;

public class Hotel extends Entity<Double>{
    private String locationId;

    private String hotelName;

    private Integer noRooms;

    private Double pricePerNight;

    private Type type;

    public Hotel(Double locationId, String hotelName, Integer noRooms, Double pricePerNight, Type type) {
        this.locationId = locationId;
        this.hotelName = hotelName;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    public Double getLocationId() {
        return locationId;
    }

    public void setLocationId(Double locationId) {
        this.locationId = locationId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getNoRooms() {
        return noRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(locationId, hotel.locationId) && Objects.equals(hotelName, hotel.hotelName) && Objects.equals(noRooms, hotel.noRooms) && Objects.equals(pricePerNight, hotel.pricePerNight) && type == hotel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, hotelName, noRooms, pricePerNight, type);
    }

    public void setNoRooms(Integer noRooms) {
        this.noRooms = noRooms;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
