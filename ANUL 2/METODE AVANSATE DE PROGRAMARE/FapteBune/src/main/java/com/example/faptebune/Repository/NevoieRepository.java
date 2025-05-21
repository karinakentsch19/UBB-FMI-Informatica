package com.example.faptebune.Repository;

import com.example.faptebune.Domain.Nevoie;
import com.example.faptebune.Domain.Persoana;
import com.example.faptebune.Validation.AbstractValidator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NevoieRepository implements AbstractRepository<Long, Nevoie> {
    private String url;
    private String username;
    private String password;

    AbstractValidator<Nevoie> validator;

    public NevoieRepository(String url, String username, String password, AbstractValidator<Nevoie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Nevoie> add(Nevoie entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Nevoi(titlu, descriere, deadline, omInNevoie, omSalvator, status) values (?, ?, ?, ?, ?, ?)")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getTitlu());
            preparedStatement.setString(2, entity.getDescriere());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDeadline()));
            preparedStatement.setLong(4, entity.getOmInNevoie());
            if (entity.getOmSalvator() == null)
                preparedStatement.setNull(5, Types.INTEGER);
            else
                preparedStatement.setLong(5, entity.getOmSalvator());
            preparedStatement.setString(6, entity.getStatus());

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
    public Optional<Nevoie> remove(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Nevoie> update(Nevoie entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update Nevoi set titlu=?, descriere=?, deadline=?, omInNevoie=?, omSalvator=?, status =? where id_nevoie = ?")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getTitlu());
            preparedStatement.setString(2, entity.getDescriere());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDeadline()));
            preparedStatement.setLong(4, entity.getOmInNevoie());
            if (entity.getOmSalvator() == null)
                preparedStatement.setNull(5, Types.INTEGER);
            else
                preparedStatement.setLong(5, entity.getOmSalvator());
            preparedStatement.setString(6, entity.getStatus());
            preparedStatement.setLong(7, entity.getId());

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
    public Iterable<Nevoie> getAll() {
        List<Nevoie> nevoieList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Nevoi");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id_nevoie = resultSet.getLong("id_nevoie");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long omInNevoie = resultSet.getLong("omInNevoie");
                Long omSalvator = resultSet.getLong("omSalvator");
                String status = resultSet.getString("status");

                Nevoie nevoie = new Nevoie(titlu, descriere, deadline, omInNevoie, omSalvator, status);
                nevoie.setId(id_nevoie);
                nevoieList.add(nevoie);
            }
            return nevoieList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Nevoie> find(Long aLong) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Nevoi where id_nevoie = ?");
        ) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id_nevoie = resultSet.getLong("id_nevoie");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long omInNevoie = resultSet.getLong("omInNevoie");
                Long omSalvator = resultSet.getLong("omSalvator");
                String status = resultSet.getString("status");

                Nevoie nevoie = new Nevoie(titlu, descriere, deadline, omInNevoie, omSalvator, status);
                nevoie.setId(id_nevoie);
                return Optional.of(nevoie);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Nevoie> getNevoiCuOmSalvatorNullDinOrasDat(String oras, Long omInNevoie) {
        List<Nevoie> nevoieList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Nevoi N join Persoane on id_persoana = omInNevoie where oras = ? and omSalvator is null and omInNevoie <> ?"); //and deadline >= now()
        ) {
            preparedStatement.setString(1, oras);
            preparedStatement.setLong(2, omInNevoie);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id_nevoie = resultSet.getLong("id_nevoie");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long omInNevoie1 = resultSet.getLong("omInNevoie");
                Long omSalvator = resultSet.getLong("omSalvator");
                String status = resultSet.getString("status");

                Nevoie nevoie = new Nevoie(titlu, descriere, deadline, omInNevoie1, omSalvator, status);
                nevoie.setId(id_nevoie);
                nevoieList.add(nevoie);
            }
            return nevoieList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Iterable<Nevoie> getNevoiPentruOmSalvatorDat(Long omSalvator) {
        List<Nevoie> nevoieList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Nevoi where omSalvator = ?");
        ) {
            preparedStatement.setLong(1, omSalvator);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id_nevoie = resultSet.getLong("id_nevoie");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long omInNevoie = resultSet.getLong("omInNevoie");
                Long omSalvator1 = resultSet.getLong("omSalvator");
                String status = resultSet.getString("status");

                Nevoie nevoie = new Nevoie(titlu, descriere, deadline, omInNevoie, omSalvator1, status);
                nevoie.setId(id_nevoie);
                nevoieList.add(nevoie);
            }
            return nevoieList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Long size() {
        return null;
    }
}
