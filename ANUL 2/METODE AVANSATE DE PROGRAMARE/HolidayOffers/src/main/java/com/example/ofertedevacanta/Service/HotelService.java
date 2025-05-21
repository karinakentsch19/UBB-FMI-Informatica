package com.example.ofertedevacanta.Service;

import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Repository.HotelRepository;

public class HotelService extends AbstractService{
    private HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Iterable<Hotel> getAllHotelsFromALocation(String locationName){
        return hotelRepository.getAllHotelsFromALocation(locationName);
    }
}
