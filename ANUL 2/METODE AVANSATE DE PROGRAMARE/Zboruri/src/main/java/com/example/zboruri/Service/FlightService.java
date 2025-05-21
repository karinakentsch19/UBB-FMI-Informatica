package com.example.zboruri.Service;

import com.example.zboruri.Domain.Flight;
import com.example.zboruri.Repository.FlightRepository;
import com.example.zboruri.Repository.Paging.Page;
import com.example.zboruri.Repository.Paging.Pageable;

import java.time.LocalDate;

public class FlightService extends AbstractService{

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Iterable<Flight> filteredFlights(String from, String to, LocalDate date){
        return flightRepository.findFlightsByFromLocationToLocationAndDepartureDay(from, to, date);
    }

    public Page<Flight> filteredFlightsOnPage(String from, String to, LocalDate date, Pageable pageable){
        return flightRepository.findFlightsByFromLocationToLocationAndDepartureDayOnPage(from, to, date, pageable);
    }

    public Iterable<String> fromLocations(){
        return flightRepository.getAllFromLocations();
    }

    public Iterable<String> toLocations(){
        return flightRepository.getAllToLocations();
    }

    public Iterable<Flight> getAll(){
        return flightRepository.getAll();
    }

    public Page<Flight> findAllOnPage(Pageable pageable){
        return flightRepository.findAllOnPage(pageable);
    }
}
