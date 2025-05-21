package com.example.trenuri.Service;

import com.example.trenuri.Domain.City;
import com.example.trenuri.Repository.CityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityService extends AbstractService{
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Iterable<City> getAll(){
        return cityRepository.getAll();
    }

    public Iterable<String> getAllCityNames(){
        List<String> names = new ArrayList<>();
        for (City city: getAll()){
            names.add(city.getName());
        }
        return names;
    }

    public City findCityById(Integer cityId){
        Optional<City> city = cityRepository.find(cityId);
        if (city.isEmpty())
            throw new RuntimeException("City doesn't exist\n");
        return city.get();
    }

    public City findCityByName(String name){
        Optional<City> city = cityRepository.findCityByName(name);
        if (city.isEmpty())
            throw new RuntimeException("City doesn't exist\n");
        return city.get();
    }
}
