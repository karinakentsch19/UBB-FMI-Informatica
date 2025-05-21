package com.example.comenzirestaurant.Repository;

import com.example.comenzirestaurant.Domain.Table;
import com.example.comenzirestaurant.Validation.AbstractValidator;
import javafx.scene.control.Tab;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableRepository implements AbstractRepository<Integer, Table> {
    private String url;
    private String username;
    private String password;

    AbstractValidator<Table> validator;

    public TableRepository(String url, String username, String password, AbstractValidator<Table> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Table> add(Table entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Table> remove(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Table> update(Table entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Table> getAll() {
        List<Table> tables = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Tables1");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 Integer id = resultSet.getInt("id_table");
                 Table table = new Table();
                 table.setId(id);
                 tables.add(table);
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Table> find(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Long size() {
        return null;
    }
}
