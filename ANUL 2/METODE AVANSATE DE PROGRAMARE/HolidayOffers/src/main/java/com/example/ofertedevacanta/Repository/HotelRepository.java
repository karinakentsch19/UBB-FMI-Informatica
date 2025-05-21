package com.example.ofertedevacanta.Repository;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Utils.Type;
import com.example.ofertedevacanta.Validation.AbstractValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelRepository implements AbstractRepository<Double, Hotel>{
    private String url;
    private String username;
    private String password;

    AbstractValidator<Hotel> validator;

    public HotelRepository(String url, String username, String password, AbstractValidator<Hotel> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Hotel> add(Hotel entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Hotel> remove(Double aDouble) {
        return Optional.empty();
    }

    @Override
    public Optional<Hotel> update(Hotel entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Hotel> getAll() {
        return null;
    }

    @Override
    public Optional<Hotel> find(Double aDouble) {
        return Optional.empty();
    }

    @Override
    public Long size() {
        return null;
    }

    public Optional<Hotel> findHotelByName(String name){
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Hotels where hotelName = ?");
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("hotelId");
                String locationName = resultSet.getString("locationName");
                String hotelName = resultSet.getString("hotelName");
                Integer noRooms = resultSet.getInt("noRooms");
                Double price = resultSet.getDouble("pricePerNight");

                Hotel hotel = new Hotel(locationName, hotelName, noRooms, price);
                hotel.setId(id);
                return Optional.of(hotel);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Hotel> getAllHotelsFromALocation(String locationName){
        List<Hotel> hotels = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Hotels H join Locations L on H.locationId = L.locationId where L.locationName = ?");
        ) {
            preparedStatement.setString(1, locationName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("hotelId");
                String locationName1 = resultSet.getString("locationName");
                String hotelName = resultSet.getString("hotelName");
                Integer noRooms = resultSet.getInt("noRooms");
                Double price = resultSet.getDouble("pricePerNight");

                Hotel hotel = new Hotel(locationName1, hotelName, noRooms, price);
                hotel.setId(id);
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
