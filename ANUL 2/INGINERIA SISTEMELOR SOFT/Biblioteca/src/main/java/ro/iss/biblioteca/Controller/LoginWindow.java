package ro.iss.biblioteca.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import ro.iss.biblioteca.Business.Observer;
import ro.iss.biblioteca.Business.Service;
import ro.iss.biblioteca.Domain.Utilizator;
import ro.iss.biblioteca.Main;

import java.io.IOException;


public class LoginWindow implements Observer {

    private Service service;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField parolaTextField;

    private String email, parola;

    private Boolean esteBibliotecar;

    private Stage currentStage;

    public void LoginWindow(Stage currentStage, Service service){
        this.service = service;
        this.currentStage = currentStage;
        service.addObserver(this);
    }

    private void completeLoginData(){
        this.email = emailTextField.getText();
        this.parola = parolaTextField.getText();
    }
    public void pressSignUpButton(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/signUpWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            SignUpWindow signUpWindow = fxmlLoader.getController();
            signUpWindow.SignUpWindow(service, currentStage);
            currentStage.setScene(scene);
            currentStage.setTitle("Sign up");
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void pressLoginButton(ActionEvent actionEvent) {
        try{
            completeLoginData();
            Utilizator utilizator = service.login(email, parola);
            if (utilizator.getEsteBibliotecar()){
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/returnBooksWindow.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                ReturnBooksWindow returnBooksWindow = fxmlLoader.getController();
                returnBooksWindow.ReturnBooksWindow(service, currentStage);
                currentStage.setTitle("Bibliotecar: " + utilizator.getNume() + " " + utilizator.getPrenume());
                currentStage.setScene(scene);
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/borrowBooksWindow.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                BorrowBooksWindow borrowBooksWindow = fxmlLoader.getController();
                borrowBooksWindow.BorrowBooksWindow(currentStage, service, utilizator);
                currentStage.setTitle(utilizator.getNume() + " " + utilizator.getPrenume());
                currentStage.setScene(scene);
            }
        }catch (IOException | RuntimeException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EROARE LA LOG IN");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void update() {

    }
}
