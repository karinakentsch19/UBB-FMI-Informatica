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
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BorrowBooksWindow implements Observer {
    private Service service;

    private Utilizator utilizator;

    private Stage currentStage;

    @FXML
    private ListView<Carte> cartiDisponibileList;

    private ObservableList<Carte> modelList = FXCollections.observableArrayList();

    public void BorrowBooksWindow(Stage currentStage, Service service, Utilizator utilizator){
        this.currentStage = currentStage;
        this.service = service;
        this.utilizator = utilizator;
        service.addObserver(this);
        initModel();
        initList();
    }

    private void initModel(){
        modelList.setAll(StreamSupport.stream(service.getAllCartiDisponibile().spliterator(), false).collect(Collectors.toList()));
    }

    private void initList(){
        cartiDisponibileList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        cartiDisponibileList.setItems(modelList);
        cartiDisponibileList.setCellFactory(lv -> new ListCell<Carte>(){
            @Override
            protected void updateItem(Carte carte, boolean empty) {
                super.updateItem(carte, empty);
                if(empty){
                    setText(null);
                }else{
                    String text = carte.getNume() + " - " + carte.getAutor() + "\n";
                    setText(text);
                }
            }
        });
    }

    public void pressLogoutButton(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/loginwindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            LoginWindow loginWindow = fxmlLoader.getController();
            loginWindow.LoginWindow(currentStage, service);
            currentStage.setScene(scene);
            currentStage.setTitle("Biblioteca");
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private List<Carte> selecteazaCarti(){
        return cartiDisponibileList.getSelectionModel().getSelectedItems();
    }

    public void pressAdaugaInCosButton(ActionEvent actionEvent) {
        try{
            List<Carte> cartiSelectate = selecteazaCarti();
            service.adaugaCartiInCosulUnuiUtilizator(cartiSelectate, utilizator);
        }catch (RuntimeException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void pressVizualizeazaCosButton(ActionEvent actionEvent){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/shoppingCart.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            ShoppingCartWindow shoppingCartWindow = fxmlLoader.getController();
            shoppingCartWindow.ShoppingCartWindow(currentStage, service, utilizator);
            currentStage.setScene(scene);
            currentStage.setTitle("Cosul meu");
        }catch (IOException e){
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
