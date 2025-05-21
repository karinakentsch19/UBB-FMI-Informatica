package com.example.zboruri.Repository;

import com.example.zboruri.Domain.Pair;
import com.example.zboruri.Domain.Ticket;
import com.example.zboruri.Validator.AbstractValidator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketRepository implements AbstractRepository<Long, Ticket>{
    private String url;
    private String username;
    private String password;

    AbstractValidator<Ticket> validator;


    public TicketRepository(String url, String username, String password, AbstractValidator<Ticket> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Ticket> add(Ticket entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Tickets(username, flightId, purchaseTime) values (?, ?, ?)")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setLong(2, entity.getFlightId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getPurchaseTime()));

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
    public Optional<Ticket> update(Ticket entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update Tickets set username = ?, flightId = ?, purchaseTime = ? where ticketId =?")
        ) {
            validator.validate(entity);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setLong(2, entity.getFlightId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getPurchaseTime()));
            preparedStatement.setLong(4, entity.getId());

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
    public Iterable<Ticket> getAll() {
        List<Ticket> ticketList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Tickets");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Long ticketId = resultSet.getLong("ticketId");
                String username = resultSet.getString("username");
                Long flightId = resultSet.getLong("flightId");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchaseTime").toLocalDateTime();
                Ticket ticket = new Ticket(username, flightId, purchaseTime);
                ticket.setId(ticketId);
                ticketList.add(ticket);
            }
            return ticketList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> find(Long id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Tickets where ticketId = ?");
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Long ticketId = resultSet.getLong("ticketId");
                String username = resultSet.getString("username");
                Long flightId = resultSet.getLong("flightId");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchaseTime").toLocalDateTime();
                Ticket ticket = new Ticket(username, flightId, purchaseTime);
                ticket.setId(ticketId);
                return Optional.of(ticket);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer numberOfTicketsSoldForAFlight(Long flightId){
        int contor = 0;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as Number from Tickets where flightId = ?");
        ) {
             preparedStatement.setLong(1, flightId);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next())
                 contor = resultSet.getInt("Number");
            return contor;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
