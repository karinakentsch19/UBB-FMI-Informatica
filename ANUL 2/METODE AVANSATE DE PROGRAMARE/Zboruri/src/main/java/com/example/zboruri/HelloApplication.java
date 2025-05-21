package com.example.zboruri;

import com.example.zboruri.Controller.LoginController;
import com.example.zboruri.Domain.Client;
import com.example.zboruri.Domain.Flight;
import com.example.zboruri.Domain.Ticket;
import com.example.zboruri.Repository.ClientRepository;
import com.example.zboruri.Repository.FlightRepository;
import com.example.zboruri.Repository.TicketRepository;
import com.example.zboruri.Service.ClientService;
import com.example.zboruri.Service.FlightService;
import com.example.zboruri.Service.TicketService;
import com.example.zboruri.Validator.AbstractValidator;
import com.example.zboruri.Validator.ClientValidator;
import com.example.zboruri.Validator.FlightValidator;
import com.example.zboruri.Validator.TicketValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("LOGIN PAGE");
        stage.setScene(scene);

        AbstractValidator<Client> clientAbstractValidator = new ClientValidator();
        ClientRepository clientRepository = new ClientRepository("jdbc:postgresql://localhost:5432/Zboruri", "postgres", "karina19", clientAbstractValidator);
        ClientService clientService = new ClientService(clientRepository);

        AbstractValidator<Flight> flightAbstractValidator = new FlightValidator();
        FlightRepository flightRepository = new FlightRepository("jdbc:postgresql://localhost:5432/Zboruri", "postgres", "karina19", flightAbstractValidator);
        FlightService flightService = new FlightService(flightRepository);

        AbstractValidator<Ticket> ticketAbstractValidator = new TicketValidator();
        TicketRepository ticketRepository = new TicketRepository("jdbc:postgresql://localhost:5432/Zboruri", "postgres", "karina19", ticketAbstractValidator);
        TicketService ticketService = new TicketService(ticketRepository, flightRepository);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(clientService, flightService, ticketService);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}