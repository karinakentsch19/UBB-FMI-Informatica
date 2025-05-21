package com.example.faptebune;

import com.example.faptebune.Controller.MainPageController;
import com.example.faptebune.Domain.Nevoie;
import com.example.faptebune.Domain.Persoana;
import com.example.faptebune.Repository.NevoieRepository;
import com.example.faptebune.Repository.PersoanaRepository;
import com.example.faptebune.Service.NevoieService;
import com.example.faptebune.Service.PersoanaService;
import com.example.faptebune.Validation.AbstractValidator;
import com.example.faptebune.Validation.NevoieValidator;
import com.example.faptebune.Validation.ValidatorPersoana;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/mainpage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("FAPTE BUNE!");
        stage.setScene(scene);

        AbstractValidator<Persoana> persoanaAbstractValidator = new ValidatorPersoana();
        PersoanaRepository persoanaRepository = new PersoanaRepository("jdbc:postgresql://localhost:5432/FapteBune", "postgres", "karina19", persoanaAbstractValidator);
        PersoanaService persoanaService = new PersoanaService(persoanaRepository);

        AbstractValidator<Nevoie> nevoieAbstractValidator = new NevoieValidator();
        NevoieRepository nevoieRepository = new NevoieRepository("jdbc:postgresql://localhost:5432/FapteBune", "postgres", "karina19", nevoieAbstractValidator);
        NevoieService nevoieService = new NevoieService(nevoieRepository);

        MainPageController mainPageController = fxmlLoader.getController();
        mainPageController.setService(persoanaService, nevoieService);

        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}