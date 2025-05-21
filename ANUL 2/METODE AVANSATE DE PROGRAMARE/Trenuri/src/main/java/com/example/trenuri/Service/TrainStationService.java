package com.example.trenuri.Service;

import com.example.trenuri.Domain.TrainStation;
import com.example.trenuri.Repository.TrainStationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TrainStationService extends AbstractService{
    private TrainStationRepository trainStationRepository;

    public TrainStationService(TrainStationRepository trainStationRepository) {
        this.trainStationRepository = trainStationRepository;
    }

    public Iterable<TrainStation> getAll(){
        return trainStationRepository.getAll();
    }

    public TrainStation findDirectRoute(Integer departureCityId, Integer destinationCityId){
        notifyAllObservers(departureCityId, destinationCityId);
        Optional<TrainStation> trainStation = trainStationRepository.findDirectRoute(departureCityId, destinationCityId);
        if (trainStation.isEmpty())
            throw new RuntimeException("Inexisting route\n");
        return trainStation.get();
    }

    private void findRoutesBacktracking(Integer departureCityId, Integer destinationCityId,  List<List<TrainStation>> routes, List<TrainStation> generatedItems){
        for(TrainStation trainStation : getAll()){
            if (Objects.equals(generatedItems.getLast().getDestinationCityId(), trainStation.getDepartureCityId()) && !generatedItems.contains(trainStation)){
                List<TrainStation> newGeneratedItems = new ArrayList<>(generatedItems);
                newGeneratedItems.add(trainStation);

                if (Objects.equals(newGeneratedItems.getLast().getDestinationCityId(), destinationCityId)){
                    routes.add(newGeneratedItems);
                }
                else{
                    findRoutesBacktracking(departureCityId, destinationCityId, routes, newGeneratedItems);
                }
            }
        }
    }

    public Iterable<List<TrainStation>> getAllNonDirectRoutes(Integer departureCityId, Integer destinationCityId){
        notifyAllObservers(departureCityId, destinationCityId);
        List<List<TrainStation>> routes = new ArrayList<>();
        for (TrainStation trainStation : getAll())
            if (Objects.equals(trainStation.getDepartureCityId(), departureCityId))
                findRoutesBacktracking(departureCityId, destinationCityId, routes, List.of(trainStation));
        return routes;
    }
}
