package com.example.ofertedevacanta.Repository;

import com.example.ofertedevacanta.Domain.DTO.ExtendedSpecialOffer;
import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Domain.Reservation;
import com.example.ofertedevacanta.Utils.Type;
import com.example.ofertedevacanta.Validation.AbstractValidator;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class ReservationRepository implements AbstractRepository<Double, Reservation>{
    private String url;
    private String username;
    private String password;

    AbstractValidator<Reservation> validator;

    public ReservationRepository(String url, String username, String password, AbstractValidator<Reservation> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Reservation> add(Reservation entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Reservations(clientId, hotelId, startDate, noNights) values (?,?,?,?)");
        ) {
            validator.validate(entity);
            preparedStatement.setLong(1, entity.getClientId());
            preparedStatement.setDouble(2, entity.getHotelId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartDate()));
            preparedStatement.setInt(4, entity.getNoNights());

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
    public Optional<Reservation> remove(Double aDouble) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> update(Reservation entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Reservation> getAll() {
        return null;
    }

    @Override
    public Optional<Reservation> find(Double aDouble) {
        return Optional.empty();
    }

    public Optional<Hotel> findHotelByName(String name){
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Hotels where hotelName = ?");
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("hotelId");
                String locationName = resultSet.getString("locationName");
                String hotelName = resultSet.getString("hotelName");
                Integer noRooms = resultSet.getInt("noRooms");
                Double price = resultSet.getDouble("pricePerNight");

                Hotel hotel = new Hotel(locationName, hotelName, noRooms, price);
                hotel.setId(id);
                return Optional.of(hotel);
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
