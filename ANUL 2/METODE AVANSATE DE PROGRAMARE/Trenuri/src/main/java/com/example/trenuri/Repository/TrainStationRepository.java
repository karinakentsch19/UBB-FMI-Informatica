package com.example.trenuri.Repository;

import com.example.trenuri.Domain.Entity;
import com.example.trenuri.Domain.TrainStation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainStationRepository implements AbstractRepository<Integer, TrainStation> {
    private String url;
    private String username;
    private String password;

    public TrainStationRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public Optional<TrainStation> add(TrainStation entity) {
        return Optional.empty();
    }

    @Override
    public Optional<TrainStation> remove(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<TrainStation> update(TrainStation entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<TrainStation> getAll() {
        List<TrainStation> trainStations = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from TrainStations");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 Integer id = resultSet.getInt("id_train");
                 Integer departureCityId = resultSet.getInt("departureCityId");
                 Integer destinationCityId = resultSet.getInt("destinationCityId");

                 TrainStation trainStation = new TrainStation(departureCityId, destinationCityId);
                 trainStation.setId(id);
                 trainStations.add(trainStation);
            }
            return trainStations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TrainStation> find(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Long size() {
        return null;
    }

    public Optional<TrainStation> findDirectRoute(Integer departureCityId, Integer destinationCityId){
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from TrainStations where departureCityId = ? and destinationCityId = ?");
        ) {
            preparedStatement.setInt(1, departureCityId);
            preparedStatement.setInt(2, destinationCityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id_train");
                Integer departureCityId1 = resultSet.getInt("departureCityId");
                Integer destinationCityId1 = resultSet.getInt("destinationCityId");

                TrainStation trainStation = new TrainStation(departureCityId1, destinationCityId1);
                trainStation.setId(id);
                return Optional.of(trainStation);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
