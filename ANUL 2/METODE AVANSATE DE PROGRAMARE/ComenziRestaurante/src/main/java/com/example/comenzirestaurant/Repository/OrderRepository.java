package com.example.comenzirestaurant.Repository;

import com.example.comenzirestaurant.Domain.DTO.PlacedOrder;
import com.example.comenzirestaurant.Domain.Order;
import com.example.comenzirestaurant.Domain.Table;
import com.example.comenzirestaurant.Utils.OrderStatus;
import com.example.comenzirestaurant.Validation.AbstractValidator;
import javafx.animation.Animation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository implements AbstractRepository<Integer, Order> {
    private String url;
    private String username;
    private String password;

    AbstractValidator<Order> validator;

    public OrderRepository(String url, String username, String password, AbstractValidator<Order> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    private Integer maxId() {
        Integer id = 0;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select max(id_order) from Orders");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> add(Order entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Orders(id_table, dateOrder, status) values (?,?,?)");
                PreparedStatement preparedStatement2 = connection.prepareStatement("insert into OrderItems(id_order, id_menuItem) values (?, ?)");
        ) {
            validator.validate(entity);
            preparedStatement.setInt(1, entity.getTableId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getDate()));
            preparedStatement.setString(3, String.valueOf(entity.getStatus()));

            int affectedRows = preparedStatement.executeUpdate();
            preparedStatement2.setInt(1, maxId());
            for (int menuItem : entity.getMenuItems()) {
                preparedStatement2.setInt(2, menuItem);
                preparedStatement2.executeUpdate();
            }
            if (affectedRows == 0)
                return Optional.of(entity);
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> remove(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> update(Order entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update Orders set id_table = ?, dateOrder = ?, status = ? where id_order = ?");

        ) {
            validator.validate(entity);
            preparedStatement.setInt(1, entity.getTableId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getDate()));
            preparedStatement.setString(3, String.valueOf(entity.getStatus()));
            preparedStatement.setInt(4, entity.getId());

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
    public Iterable<Order> getAll() {
        return null;
    }

    @Override
    public Optional<Order> find(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Long size() {
        return null;
    }

    public Iterable<PlacedOrder> getAllOrdersByStatus(OrderStatus status) {
        List<PlacedOrder> placedOrderList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select O.id_order, O.id_table, O.dateOrder from Orders O where O.status = ? order by O.dateOrder");
                PreparedStatement preparedStatement1 = connection.prepareStatement("select MI.item from MenuItems MI join OrderItems OI on OI.id_menuItem = MI.id_menuItem join Orders O on O.id_order = OI.id_order where O.id_order = ?");
        ) {
            preparedStatement.setString(1, String.valueOf(status));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer orderId = resultSet.getInt("id_order");
                Integer tableId = resultSet.getInt("id_table");
                LocalDateTime date = resultSet.getTimestamp("dateOrder").toLocalDateTime();

                preparedStatement1.setInt(1, orderId);
                List<String> menuItemNames = new ArrayList<>();
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    String item = resultSet1.getString("item");
                    menuItemNames.add(item);
                }
                PlacedOrder placedOrder = new PlacedOrder(orderId, tableId, date, menuItemNames);
                placedOrderList.add(placedOrder);
            }
            return placedOrderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
