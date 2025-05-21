package com.example.ofertedevacanta.Repository;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Utils.Hobby;
import com.example.ofertedevacanta.Validation.AbstractValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements AbstractRepository<Long, Client>{
    private String url;
    private String username;
    private String password;

    AbstractValidator<Client> validator;

    public ClientRepository(String url, String username, String password, AbstractValidator<Client> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Client> add(Client entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> remove(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Client> getAll() {
        return null;
    }

    public Iterable<Client> getAllClientsExceptOne(Long clientId){
        List<Client> clients = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Clients where clientId <> ?");
        ) {
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long clientId1 = resultSet.getLong("clientId");
                String name = resultSet.getString("name");
                Integer fidelityGrade = resultSet.getInt("fidelityGrade");
                Integer age = resultSet.getInt("age");
                Hobby hobby = Hobby.valueOf(resultSet.getString("hobbies"));

                Client client = new Client(name, fidelityGrade, age, hobby);
                client.setId(clientId1);
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> find(Long aLong) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Clients where clientId = ?");
        ) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long clientId = resultSet.getLong("clientId");
                String name = resultSet.getString("name");
                Integer fidelityGrade = resultSet.getInt("fidelityGrade");
                Integer age = resultSet.getInt("age");
                Hobby hobby = Hobby.valueOf(resultSet.getString("hobbies"));

                Client client = new Client(name, fidelityGrade, age, hobby);
                client.setId(clientId);
                return Optional.of(client);
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
