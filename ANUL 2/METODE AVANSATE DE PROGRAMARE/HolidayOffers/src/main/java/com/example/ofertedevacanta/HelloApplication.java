package com.example.ofertedevacanta;

import com.example.ofertedevacanta.Controller.ClientPageController;
import com.example.ofertedevacanta.Domain.*;
import com.example.ofertedevacanta.Repository.*;
import com.example.ofertedevacanta.Service.*;
import com.example.ofertedevacanta.Validation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static String[] clientIds;

    @Override
    public void start(Stage stage) throws IOException {
        AbstractValidator<Client> clientAbstractValidator = new ClientValidator();
        ClientRepository clientRepository = new ClientRepository("jdbc:postgresql://localhost:5432/OferteDeVacanta", "postgres", "karina19", clientAbstractValidator);
        ClientService clientService = new ClientService(clientRepository);

        AbstractValidator<Hotel> hotelAbstractValidator = new HotelValidator();
        HotelRepository hotelRepository = new HotelRepository("jdbc:postgresql://localhost:5432/OferteDeVacanta", "postgres", "karina19", hotelAbstractValidator);
        HotelService hotelService = new HotelService(hotelRepository);

        AbstractValidator<Location> locationAbstractValidator = new LocationValidator();
        LocationRepository locationRepository = new LocationRepository("jdbc:postgresql://localhost:5432/OferteDeVacanta", "postgres", "karina19", locationAbstractValidator);
        LocationService locationService = new LocationService(locationRepository);

        AbstractValidator<Reservation> reservationAbstractValidator = new ReservationValidator();
        ReservationRepository reservationRepository = new ReservationRepository("jdbc:postgresql://localhost:5432/OferteDeVacanta", "postgres", "karina19", reservationAbstractValidator);
        ReservationService reservationService = new ReservationService(reservationRepository, clientRepository);

        AbstractValidator<SpecialOffer> specialOfferAbstractValidator = new SpecialOfferValidator();
        SpecialOfferRepository specialOfferRepository = new SpecialOfferRepository("jdbc:postgresql://localhost:5432/OferteDeVacanta", "postgres", "karina19", specialOfferAbstractValidator);
        SpecialOfferService specialOfferService = new SpecialOfferService(specialOfferRepository);


        for (String clientId : clientIds){
            Long clientID = Long.valueOf(clientId);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/clientPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage newStage = new Stage();

            Client client = clientService.findClient(clientID);
            newStage.setTitle(client.getName());
            newStage.setScene(scene);

            ClientPageController clientPageController = fxmlLoader.getController();
            clientPageController.setService(clientService, hotelService, locationService, reservationService, specialOfferService, client);

            newStage.show();
        }

    }

    public static void main(String[] args) {
        clientIds = args;
        launch();
    }
}