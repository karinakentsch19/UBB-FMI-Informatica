package com.example.trenuri;

import com.example.trenuri.Controller.MainPageController;
import com.example.trenuri.Domain.City;
import com.example.trenuri.Repository.CityRepository;
import com.example.trenuri.Repository.TrainStationRepository;
import com.example.trenuri.Service.CityService;
import com.example.trenuri.Service.TrainStationService;
import com.example.trenuri.Validator.AbstractValidator;
import com.example.trenuri.Validator.CityValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/mainPage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("MAIN WINDOW");
        stage.setScene(scene);

        AbstractValidator<City> cityAbstractValidator = new CityValidator();
        CityRepository cityRepository = new CityRepository("jdbc:postgresql://localhost:5432/Trenuri", "postgres", "karina19", cityAbstractValidator);
        CityService cityService = new CityService(cityRepository);

        TrainStationRepository trainStationRepository = new TrainStationRepository("jdbc:postgresql://localhost:5432/Trenuri", "postgres", "karina19");
        TrainStationService trainStationService = new TrainStationService(trainStationRepository);

        MainPageController mainPageController = fxmlLoader.getController();
        mainPageController.setService(cityService, trainStationService);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}