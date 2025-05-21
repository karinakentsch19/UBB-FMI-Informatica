package com.example.trenuri.Controller;

import com.example.trenuri.Domain.City;
import com.example.trenuri.HelloApplication;
import com.example.trenuri.Repository.CityRepository;
import com.example.trenuri.Repository.TrainStationRepository;
import com.example.trenuri.Service.CityService;
import com.example.trenuri.Service.TrainStationService;
import com.example.trenuri.Validator.AbstractValidator;
import com.example.trenuri.Validator.CityValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainPageController {

    private CityService cityService;

    private TrainStationService trainStationService;

    public void setService(CityService cityService, TrainStationService trainStationService){
        this.cityService = cityService;
        this.trainStationService = trainStationService;
    }

    public void onPressOpenNewClientWindowButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/clientPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);

            ClientPageController clientPageController = fxmlLoader.getController();
            clientPageController.setService(cityService, trainStationService);
            stage.show();
        }catch (IOException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }
}
