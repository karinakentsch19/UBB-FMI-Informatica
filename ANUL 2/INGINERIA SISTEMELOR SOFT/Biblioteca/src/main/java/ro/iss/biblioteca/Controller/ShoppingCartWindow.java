package ro.iss.biblioteca.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ro.iss.biblioteca.Business.Observable;
import ro.iss.biblioteca.Business.Observer;
import ro.iss.biblioteca.Business.Service;
import ro.iss.biblioteca.Domain.Carte;
import ro.iss.biblioteca.Domain.Utilizator;
import ro.iss.biblioteca.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ShoppingCartWindow implements Observer {

    private Stage currentStage;

    private Utilizator utilizator;

    private Service service;

    @FXML
    private ListView<Carte> cosList;

    private ObservableList<Carte> modelList = FXCollections.observableArrayList();

    public void ShoppingCartWindow(Stage currentStage, Service service, Utilizator utilizator) {
        this.currentStage = currentStage;
        this.service = service;
        this.utilizator = utilizator;
        service.addObserver(this);
        initModel();
        initList();
    }

    private void initList() {
        cosList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        cosList.setItems(modelList);
        cosList.setCellFactory(lv -> new ListCell<Carte>() {
            @Override
            protected void updateItem(Carte carte, boolean empty) {
                super.updateItem(carte, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = carte.getNume() + " - " + carte.getAutor() + "\n";
                    setText(text);
                }
            }
        });
    }

    private void initModel() {
        modelList.setAll(StreamSupport.stream(service.getAllCartiDinCosulUnuiUtilizator(utilizator).spliterator(), false).collect(Collectors.toList()));
    }

    public void pressBackButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/borrowBooksWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            BorrowBooksWindow borrowBooksWindow = fxmlLoader.getController();
            borrowBooksWindow.BorrowBooksWindow(currentStage, service, utilizator);
            currentStage.setTitle(utilizator.getNume() + " " + utilizator.getPrenume());
            currentStage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void pressStergeCartiButton(ActionEvent actionEvent) {
        try {
            List<Carte> carti = cosList.getSelectionModel().getSelectedItems();

            if (carti.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("COS GOL");
                alert.setContentText("Stergerea nu poate fi facuta daca nu sunt carti in cos!");
                alert.showAndWait();
            }
            else {
                service.stergeCartiDinCosulUnuiUtilizator(carti, utilizator);
            }
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void pressImprumutaButton(ActionEvent actionEvent) {
        try {
            List<Carte> carti = new ArrayList<>();
            for (Carte carte : service.getAllCartiDinCosulUnuiUtilizator(utilizator)) {
                carti.add(carte);
            }
            service.stergeToateCartileDinCosulUnuiUtilizator(utilizator);

            if (!carti.isEmpty()) {
                service.imprumutaCarti(carti, utilizator);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("CARTI IMPRUMUTATE CU SUCCES");
                alert.setContentText("Imprumutul tau a fost realizat cu succes!");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("COS GOL");
                alert.setContentText("Imprumutul nu poate fi facut daca nu sunt carti in cos!");
                alert.showAndWait();
            }
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void update() {
        initModel();
    }
}
