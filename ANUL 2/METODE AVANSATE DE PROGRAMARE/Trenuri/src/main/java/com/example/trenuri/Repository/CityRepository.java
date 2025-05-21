package com.example.trenuri.Repository;

import com.example.trenuri.Domain.City;
import com.example.trenuri.Validator.AbstractValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRepository implements AbstractRepository<Integer, City>{
    private String url;
    private String username;
    private String password;

    AbstractValidator<City> validator;

    public CityRepository(String url, String username, String password, AbstractValidator<City> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<City> add(City entity) {
        return Optional.empty();
    }

    @Override
    public Optional<City> remove(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<City> update(City entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<City> getAll() {
        List<City> cities = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Cities");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id_city");
                String name = resultSet.getString("name");

                City city = new City(name);
                city.setId(id);
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<City> find(Integer integer) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Cities where id_city = ?");
        ) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id_city");
                String name = resultSet.getString("name");

                City city = new City(name);
                city.setId(id);
                return Optional.of(city);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<City> findCityByName(String name){
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Cities where name = ?");
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id_city");
                String name1 = resultSet.getString("name");

                City city = new City(name1);
                city.setId(id);
                return Optional.of(city);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long size() {
        return null;
    }
}
