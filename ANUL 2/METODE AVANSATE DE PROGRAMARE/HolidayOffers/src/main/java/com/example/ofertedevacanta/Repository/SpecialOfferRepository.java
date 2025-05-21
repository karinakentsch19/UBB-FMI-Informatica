package com.example.ofertedevacanta.Repository;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Domain.DTO.ExtendedSpecialOffer;
import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Domain.SpecialOffer;
import com.example.ofertedevacanta.Utils.Type;
import com.example.ofertedevacanta.Validation.AbstractValidator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialOfferRepository implements AbstractRepository<Double, SpecialOffer> {
    private String url;
    private String username;
    private String password;

    AbstractValidator<SpecialOffer> validator;

    public SpecialOfferRepository(String url, String username, String password, AbstractValidator<SpecialOffer> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<SpecialOffer> add(SpecialOffer entity) {
        return Optional.empty();
    }

    @Override
    public Optional<SpecialOffer> remove(Double aDouble) {
        return Optional.empty();
    }

    @Override
    public Optional<SpecialOffer> update(SpecialOffer entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<SpecialOffer> getAll() {
        return null;
    }

    @Override
    public Optional<SpecialOffer> find(Double aDouble) {
        return Optional.empty();
    }

    @Override
    public Long size() {
        return null;
    }

    public Iterable<SpecialOffer> getAllSpecialOffersFromADateAndAHotel(LocalDate startDate, LocalDate endDate, Double hotelId) {
        List<SpecialOffer> specialOffers = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from SpecialOffers where startDate = ? and endDate = ? and hotelId = ?");
        ) {
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));
            preparedStatement.setDouble(3, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double specialOfferId = resultSet.getDouble("specialOfferId");
                Double hotelId1 = resultSet.getDouble("hotelId");
                LocalDate startDate1 = resultSet.getDate("startDate").toLocalDate();
                LocalDate endDate1 = resultSet.getDate("endDate").toLocalDate();
                Integer percents = resultSet.getInt("percents");

                SpecialOffer specialOffer = new SpecialOffer(hotelId1, startDate1, endDate1, percents);
                specialOffer.setId(specialOfferId);
                specialOffers.add(specialOffer);
            }
            return specialOffers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<ExtendedSpecialOffer> getAllSpecialOffersForAClient(Integer fidelityGrade) {
        List<ExtendedSpecialOffer> specialOffers = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select SO.specialOfferId, SO.startDate, SO.endDate, SO.percents, H.hotelName from SpecialOffers SO join Hotels H on H.hotelId = SO.hotelId where ? > percents and endDate >= current_date");
        ) {
            preparedStatement.setInt(1, fidelityGrade);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double specialOfferId = resultSet.getDouble("specialOfferId");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                Integer percents = resultSet.getInt("percents");
                String hotelName = resultSet.getString("hotelName");

                ExtendedSpecialOffer specialOffer = new ExtendedSpecialOffer(hotelName, startDate, endDate, percents);
                specialOffers.add(specialOffer);
            }
            return specialOffers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
