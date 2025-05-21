package com.example.comenzirestaurant.Repository;

import com.example.comenzirestaurant.Domain.MenuItem;
import com.example.comenzirestaurant.Domain.Table;
import com.example.comenzirestaurant.Validation.AbstractValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class MenuItemRepository implements AbstractRepository<Integer, MenuItem>{
    private String url;
    private String username;
    private String password;

    AbstractValidator<MenuItem> validator;

    public MenuItemRepository(String url, String username, String password, AbstractValidator<MenuItem> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<MenuItem> add(MenuItem entity) {
        return Optional.empty();
    }

    @Override
    public Optional<MenuItem> remove(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<MenuItem> update(MenuItem entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<MenuItem> getAll() {
        return null;
    }

    @Override
    public Optional<MenuItem> find(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Long size() {
        return null;
    }

    public Iterable<MenuItem> getAllMenuItemsByCategory(String category){
        List<MenuItem> menuItemList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from MenuItems where category = ?");
        ) {
            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 Integer id = resultSet.getInt("id_menuItem");
                 String category1 = resultSet.getString("category");
                 String item = resultSet.getString("item");
                 Float price = resultSet.getFloat("price");
                 String currency = resultSet.getString("currency");

                 MenuItem menuItem = new MenuItem(category1, item, price, currency);
                 menuItem.setId(id);
                 menuItemList.add(menuItem);
            }
            return menuItemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<String> getAllCategories(){
        List<String> categories = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select distinct category from MenuItems");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 String category = resultSet.getString("category");
                 categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
