package com.example.zboruri.Controller;

import com.example.zboruri.Domain.Client;
import com.example.zboruri.HelloApplication;
import com.example.zboruri.Service.ClientService;
import com.example.zboruri.Service.FlightService;
import com.example.zboruri.Service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    private ClientService clientService;

    private FlightService flightService;

    private TicketService ticketService;


    public void setService(ClientService clientService, FlightService flightService, TicketService ticketService) {
        this.clientService = clientService;
        this.flightService = flightService;
        this.ticketService = ticketService;
    }

    public void onPressLoginButton(ActionEvent actionEvent) throws IOException {
        try {
            String username = usernameTextField.getText();
            Client client = clientService.findClientByUsername(username);

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/clientpage-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 400);
            stage.setTitle(client.getName());
            stage.setScene(scene);

            ClientPageController clientPage = loader.getController();
            clientPage.setService(clientService, flightService, ticketService, client);

            stage.show();
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
        finally {
            usernameTextField.clear();
        }

    }
}
