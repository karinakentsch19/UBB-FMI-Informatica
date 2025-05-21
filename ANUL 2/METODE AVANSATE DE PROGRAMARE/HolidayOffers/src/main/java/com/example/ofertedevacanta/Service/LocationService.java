package com.example.ofertedevacanta.Service;

import com.example.ofertedevacanta.Domain.Location;
import com.example.ofertedevacanta.Repository.LocationRepository;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LocationService extends AbstractService{
    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Iterable<String> getAllLocations(){
        Function<Location, String> locationName = Location::getLocationName;
        return StreamSupport.stream(locationRepository.getAll().spliterator(), false).map(locationName).collect(Collectors.toList());
    }
}
