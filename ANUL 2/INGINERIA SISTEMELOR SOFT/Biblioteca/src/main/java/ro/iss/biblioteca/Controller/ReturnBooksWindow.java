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
import ro.iss.biblioteca.Business.Observer;
import ro.iss.biblioteca.Business.Service;
import ro.iss.biblioteca.Domain.Carte;
import ro.iss.biblioteca.Domain.Imprumut;
import ro.iss.biblioteca.Domain.ImprumutDTO;
import ro.iss.biblioteca.Domain.Utilizator;
import ro.iss.biblioteca.Main;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReturnBooksWindow implements Observer {
    private Service service;

    private Stage currentStage;

    @FXML
    private ListView<ImprumutDTO> cartiImprumutateList;

    private ObservableList<ImprumutDTO> modelList = FXCollections.observableArrayList();

    public void ReturnBooksWindow(Service service, Stage stage){
        this.service = service;
        this.currentStage = stage;
        service.addObserver(this);
        initModel();
        initList();
    }

    private void initList(){
        cartiImprumutateList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        cartiImprumutateList.setItems(modelList);
        cartiImprumutateList.setCellFactory(lv -> new ListCell<ImprumutDTO>() {
            @Override
            protected void updateItem(ImprumutDTO imprumutDTO, boolean empty) {
                super.updateItem(imprumutDTO, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text =  imprumutDTO.getNumeCarte() + " - " + imprumutDTO.getAutor() + " -> " + imprumutDTO.getNumeUtilizator() + " " + imprumutDTO.getPrenumeUtilizator();
                    setText(text);
                }
            }
        });
    }

    private void initModel(){
        modelList.setAll(StreamSupport.stream(service.getAllCartiImprumutate().spliterator(), false).collect(Collectors.toList()));
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

    private List<ImprumutDTO> selecteazaCarti(){
        return cartiImprumutateList.getSelectionModel().getSelectedItems();
    }

    public void pressRestituieButton(ActionEvent actionEvent) {
        try{
            List<ImprumutDTO> cartiDeReturnat = selecteazaCarti();
            service.returneazaCarti(cartiDeReturnat);
        }catch (RuntimeException e){
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
