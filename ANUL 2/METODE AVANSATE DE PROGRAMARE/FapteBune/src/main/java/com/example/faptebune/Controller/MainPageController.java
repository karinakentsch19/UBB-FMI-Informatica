package com.example.faptebune.Controller;

import com.example.faptebune.Domain.Persoana;
import com.example.faptebune.HelloApplication;
import com.example.faptebune.Repository.NevoieRepository;
import com.example.faptebune.Service.NevoieService;
import com.example.faptebune.Service.PersoanaService;
import com.example.faptebune.Utils.Observer;
import com.example.faptebune.Utils.Orase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainPageController implements Observer {

    @FXML
    private TextField numeTextfield;


    @FXML
    private TextField prenumeTextfield;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField parolaTextfield;

    @FXML
    private TextField stradaTextfield;

    @FXML
    private TextField numarStradaTextfield;

    @FXML
    private TextField telefonTextField;

    @FXML
    private ComboBox<Orase> orasComboBox;

    private PersoanaService persoanaService;

    private NevoieService nevoieService;

    @FXML
    private ObservableList<String> usernameModel = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Orase> comboboxModel = FXCollections.observableArrayList();

    @FXML
    private ListView<String> usernameList;

    public void setService(PersoanaService persoanaService, NevoieService nevoieService){
        this.persoanaService = persoanaService;
        this.nevoieService = nevoieService;
        initializeListModel();
        initializeList();
        setComboBoxModel();
        setOraseComboBox();
        persoanaService.addObserver(this);
    }

    private void setOraseComboBox(){
        orasComboBox.setItems(comboboxModel);
    }

    private void setComboBoxModel(){
        comboboxModel.setAll(StreamSupport.stream(Arrays.stream(Orase.values()).spliterator(), false).collect(Collectors.toList()));
    }
    private void initializeListModel(){
        usernameModel.setAll(StreamSupport.stream(persoanaService.getAllUsernames().spliterator(), false).collect(Collectors.toList()));
    }

    private void initializeList(){
        usernameList.setItems(usernameModel);
    }

    private void clearFields(){
        numeTextfield.clear();
        prenumeTextfield.clear();
        usernameTextfield.clear();
        parolaTextfield.clear();
        stradaTextfield.clear();
        numarStradaTextfield.clear();
        numeTextfield.clear();
        telefonTextField.clear();
    }
    public void onPressSignUpButton(ActionEvent actionEvent) {
        try{
            String nume = numeTextfield.getText();
            String prenume = prenumeTextfield.getText();
            String username = usernameTextfield.getText();
            String parola = parolaTextfield.getText();
            String strada = stradaTextfield.getText();
            String numarStrada = numarStradaTextfield.getText();
            String telefon = telefonTextField.getText();
            String oras = String.valueOf(orasComboBox.getValue());
            persoanaService.addPersoana(nume, prenume, username, parola, oras, strada, numarStrada, telefon);
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
        finally {
            clearFields();
        }

    }

    @Override
    public void update() {
        initializeListModel();
    }

    public void openAUsersPage(javafx.scene.input.MouseEvent mouseEvent){
        try {
             String username = usernameList.getSelectionModel().getSelectedItem();
             Persoana persoana = persoanaService.persoanaCuUsernameDat(username);

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/mypage-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 400);
            stage.setTitle(persoana.getNume() + " " + persoana.getPrenume());
            stage.setScene(scene);

            MyPageController myPageController = loader.getController();
            myPageController.setService(persoanaService, nevoieService, persoana);

            stage.show();
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
