package com.example.faptebune.Repository;

import com.example.faptebune.Domain.Persoana;
import com.example.faptebune.Validation.AbstractValidator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class PersoanaRepository implements AbstractRepository<Long, Persoana> {
    private String url;
    private String username;
    private String password;

    AbstractValidator<Persoana> validator;

    public PersoanaRepository(String url, String username, String password, AbstractValidator<Persoana> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Persoana> add(Persoana entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Persoane(nume, prenume, username, parola, oras, strada, numarStrada, telefon) values (?, ?, ?, ?, ?, ?, ?, ?)")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getNume());
            preparedStatement.setString(2, entity.getPrenume());
            preparedStatement.setString(3, entity.getUsername());
            preparedStatement.setString(4, entity.getParola());
            preparedStatement.setString(5, entity.getOras());
            preparedStatement.setString(6, entity.getStrada());
            preparedStatement.setString(7, entity.getNumarStrada());
            preparedStatement.setString(8, entity.getTelefon());

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
    public Optional<Persoana> remove(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Persoana> update(Persoana entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update Persoane set nume = ?, prenume = ?, username = ?, parola = ?, oras = ?, strada = ?, numarStrada = ?, telefon = ? where id_persoana = ?")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getNume());
            preparedStatement.setString(2, entity.getPrenume());
            preparedStatement.setString(3, entity.getUsername());
            preparedStatement.setString(4, entity.getParola());
            preparedStatement.setString(5, entity.getOras());
            preparedStatement.setString(6, entity.getStrada());
            preparedStatement.setString(7, entity.getNumarStrada());
            preparedStatement.setString(8, entity.getTelefon());
            preparedStatement.setLong(9, entity.getId());

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
    public Iterable<Persoana> getAll() {
        List<Persoana> persoanaList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Persoane");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id_persoana = resultSet.getLong("id_persoana");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                String oras = resultSet.getString("oras");
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarStrada");
                String telefon = resultSet.getString("telefon");

                Persoana persoana = new Persoana(nume, prenume, username, parola, oras, strada, numarStrada, telefon);
                persoana.setId(id_persoana);
                persoanaList.add(persoana);
            }
            return persoanaList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> find(Long aLong) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Persoane where id_persoana = ?");
        ) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id_persoana = resultSet.getLong("id_persoana");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                String oras = resultSet.getString("oras");
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarStrada");
                String telefon = resultSet.getString("telefon");

                Persoana persoana = new Persoana(nume, prenume, username, parola, oras, strada, numarStrada, telefon);
                persoana.setId(id_persoana);
                return Optional.of(persoana);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select username from Persoane");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username1 = resultSet.getString("username");
                usernames.add(username1);
            }
            return usernames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Persoana> findPersoanaByUsername(String usernamePersoana) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Persoane where username = ?");
        ) {
            preparedStatement.setString(1, usernamePersoana);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id_persoana = resultSet.getLong("id_persoana");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                String oras = resultSet.getString("oras");
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarStrada");
                String telefon = resultSet.getString("telefon");

                Persoana persoana = new Persoana(nume, prenume, username, parola, oras, strada, numarStrada, telefon);
                persoana.setId(id_persoana);
                return Optional.of(persoana);
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
