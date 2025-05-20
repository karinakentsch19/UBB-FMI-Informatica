package ro.iss.biblioteca.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.iss.biblioteca.Business.Service;
import ro.iss.biblioteca.Main;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpWindow {
    private Service service;

    private Stage currentStage;

    @FXML
    private TextField numeTextField;

    @FXML
    private TextField prenumeTextField;

    @FXML
    private TextField cnpTextField;

    @FXML
    private TextField adresaTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField telefonTextField;

    @FXML
    private TextField parolaTextField;

    private String nume, prenume, adresa, email, telefon, parola;

    private Long cnp;

    public void SignUpWindow(Service service, Stage stage){
        this.service = service;
        this.currentStage = stage;
    }

    private void completeSignUpData(){
        this.nume = numeTextField.getText();
        this.prenume = prenumeTextField.getText();
        this.cnp = Long.valueOf(cnpTextField.getText());
        this.adresa = adresaTextField.getText();
        this.email = emailTextField.getText();
        this.telefon = telefonTextField.getText();
        this.parola = parolaTextField.getText();
    }

    public void pressCreateAccountButton(ActionEvent actionEvent) {
        try{
            completeSignUpData();
            service.signUp(nume, prenume, cnp, adresa, email, telefon, parola);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/loginwindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            LoginWindow loginWindow = fxmlLoader.getController();
            loginWindow.LoginWindow(currentStage, service);
            currentStage.setScene(scene);
            currentStage.setTitle("Biblioteca");
        } catch (IOException | RuntimeException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
