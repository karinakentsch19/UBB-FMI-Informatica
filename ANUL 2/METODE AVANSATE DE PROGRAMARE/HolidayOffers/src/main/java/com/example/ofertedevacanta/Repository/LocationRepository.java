package com.example.ofertedevacanta.Repository;

import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Domain.Location;
import com.example.ofertedevacanta.Utils.Type;
import com.example.ofertedevacanta.Validation.AbstractValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepository implements AbstractRepository<Double, Location>{
    private String url;
    private String username;
    private String password;

    AbstractValidator<Location> validator;

    public LocationRepository(String url, String username, String password, AbstractValidator<Location> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Location> add(Location entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Location> remove(Double aDouble) {
        return Optional.empty();
    }

    @Override
    public Optional<Location> update(Location entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Location> getAll() {
        List<Location> locations = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Locations");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double locationId = resultSet.getDouble("locationId");
                String locationName = resultSet.getString("locationName");
                Location location = new Location(locationName);
                location.setId(locationId);
                locations.add(location);
            }
            return locations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Location> find(Double aDouble) {
        return Optional.empty();
    }

    @Override
    public Long size() {
        return null;
    }
}
