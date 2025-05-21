package com.example.zboruri.Repository;

import com.example.zboruri.Domain.Client;
import com.example.zboruri.Validator.AbstractValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class ClientRepository implements AbstractRepository<Long, Client> {
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
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Clients(username, name) values (?,?) ")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getName());

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
    public Optional<Client> update(Client entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update Clients set name = ? where username = ?")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getUsername());

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
    public Iterable<Client> getAll() {
        List<Client> clientList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Clients");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                Client client = new Client(username, name);
                clientList.add(client);
            }
            return clientList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> find(Long aLong) {
        return Optional.empty();
    }


    public Optional<Client> findClient(String clientUsername) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Clients where username = ?");
        ) {
            preparedStatement.setString(1, clientUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String clientUsername1 = resultSet.getString("username");
                String name = resultSet.getString("name");

                Client client = new Client(clientUsername1, name);
                return Optional.of(client);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
