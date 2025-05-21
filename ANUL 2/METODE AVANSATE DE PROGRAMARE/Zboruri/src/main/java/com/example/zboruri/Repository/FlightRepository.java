package com.example.zboruri.Repository;

import com.example.zboruri.Domain.Flight;
import com.example.zboruri.Repository.Paging.Page;
import com.example.zboruri.Repository.Paging.Pageable;
import com.example.zboruri.Repository.Paging.PagingRepository;
import com.example.zboruri.Validator.AbstractValidator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightRepository implements PagingRepository<Long, Flight> {

    private String url;
    private String username;
    private String password;

    AbstractValidator<Flight> validator;

    public FlightRepository(String url, String username, String password, AbstractValidator<Flight> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Flight> add(Flight entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Flights(fromLocation, toLocation, departureTime, landingTime, seats) values (?,?, ?, ?, ?) ")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getFrom());
            preparedStatement.setString(2, entity.getTo());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDepartureTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getLandingTime()));
            preparedStatement.setInt(5, entity.getSeats());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0)
                return Optional.of(entity);
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Flight> update(Flight entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update Flights set fromLocation=?, toLocation=?, departureTime=?, landingTime=?, seats =? where flightId = ?")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getFrom());
            preparedStatement.setString(2, entity.getTo());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDepartureTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getLandingTime()));
            preparedStatement.setInt(5, entity.getSeats());
            preparedStatement.setLong(6, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0)
                return Optional.of(entity);
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Flight> getAll() {
        List<Flight> flightList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Flights");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Long flightId = resultSet.getLong("flightId");
                String from = resultSet.getString("fromLocation");
                String to = resultSet.getString("toLocation");
                LocalDateTime departureTime = resultSet.getTimestamp("departureTime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingTime").toLocalDateTime();
                Integer seats = resultSet.getInt("seats");
                Flight flight = new Flight(from, to, departureTime, landingTime, seats);
                flight.setId(flightId);
                flightList.add(flight);
            }
            return flightList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Flight> find(Long aLong) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Flights where flightId = ?");
        ) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Long flightId = resultSet.getLong("flightId");
                String from = resultSet.getString("fromLocation");
                String to = resultSet.getString("toLocation");
                LocalDateTime departureTime = resultSet.getTimestamp("departureTime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingTime").toLocalDateTime();
                Integer seats = resultSet.getInt("seats");
                Flight flight = new Flight(from, to, departureTime, landingTime, seats);
                flight.setId(flightId);
                return Optional.of(flight);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Flight> findFlightsByFromLocationToLocationAndDepartureDay(String from, String to, LocalDate date) {
        List<Flight> flightList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Flights where fromLocation = ? and toLocation = ? and date(departureTime) = ?");
        ) {
            preparedStatement.setString(1, from);
            preparedStatement.setString(2, to);
            preparedStatement.setDate(3, Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Long flightId = resultSet.getLong("flightId");
                String fromLocation = resultSet.getString("fromLocation");
                String toLocation = resultSet.getString("toLocation");
                LocalDateTime departureTime = resultSet.getTimestamp("departureTime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingTime").toLocalDateTime();
                Integer seats = resultSet.getInt("seats");
                Flight flight = new Flight(fromLocation, toLocation, departureTime, landingTime, seats);
                flight.setId(flightId);
                flightList.add(flight);
            }
            return flightList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<String> getAllFromLocations() {
        List<String> fromLocations = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select distinct fromLocation from Flights");
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String from = resultSet.getString("fromLocation");
                fromLocations.add(from);
            }
            return fromLocations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<String> getAllToLocations() {
        List<String> toLocations = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select distinct toLocation from Flights");
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String to = resultSet.getString("toLocation");
                toLocations.add(to);
            }
            return toLocations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Flight> findAllOnPage(Pageable pageable) {
        List<Flight> flightList = new ArrayList<>();
        int size = 0;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Flights limit ? offset ?");
                PreparedStatement preparedStatement2 = connection.prepareStatement("select count(*) from Flights")
        ) {
            preparedStatement.setInt(1, pageable.getPageSize());
            preparedStatement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Long flightId = resultSet.getLong("flightId");
                String from = resultSet.getString("fromLocation");
                String to = resultSet.getString("toLocation");
                LocalDateTime departureTime = resultSet.getTimestamp("departureTime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingTime").toLocalDateTime();
                Integer seats = resultSet.getInt("seats");
                Flight flight = new Flight(from, to, departureTime, landingTime, seats);
                flight.setId(flightId);
                flightList.add(flight);
            }

            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()){
                size = resultSet2.getInt(1);
            }
            return new Page<>(flightList, size);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Page<Flight> findFlightsByFromLocationToLocationAndDepartureDayOnPage(String from, String to, LocalDate date, Pageable pageable) {
        List<Flight> flightList = new ArrayList<>();
        int size = 0;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Flights where fromLocation = ? and toLocation = ? and date(departureTime) = ? limit ? offset ?");
                PreparedStatement preparedStatement2 = connection.prepareStatement("select count(*) from Flights where fromLocation = ? and toLocation = ? and date(departureTime) = ?")
        ) {
            preparedStatement.setString(1, from);
            preparedStatement.setString(2, to);
            preparedStatement.setDate(3, Date.valueOf(date));
            preparedStatement.setInt(4, pageable.getPageSize());
            preparedStatement.setInt(5, pageable.getPageSize() * pageable.getPageNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Long flightId = resultSet.getLong("flightId");
                String fromL = resultSet.getString("fromLocation");
                String toL = resultSet.getString("toLocation");
                LocalDateTime departureTime = resultSet.getTimestamp("departureTime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingTime").toLocalDateTime();
                Integer seats = resultSet.getInt("seats");
                Flight flight = new Flight(fromL, toL, departureTime, landingTime, seats);
                flight.setId(flightId);
                flightList.add(flight);
            }

            preparedStatement2.setString(1, from);
            preparedStatement2.setString(2, to);
            preparedStatement2.setDate(3, Date.valueOf(date));
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()){
                size = resultSet2.getInt(1);
            }
            return new Page<>(flightList, size);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
