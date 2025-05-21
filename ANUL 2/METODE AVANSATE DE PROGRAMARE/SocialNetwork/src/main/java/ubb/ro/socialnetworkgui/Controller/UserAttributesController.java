package ubb.ro.socialnetworkgui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Exceptions.ExistingEntityException;
import ubb.ro.socialnetworkgui.Exceptions.InexistingEntityException;
import ubb.ro.socialnetworkgui.Exceptions.ValidationException;
import ubb.ro.socialnetworkgui.Service.UserService;
import ubb.ro.socialnetworkgui.UsefulTools.WindowStrategy;

public class UserAttributesController {
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField passwordTextField;

    private UserService userService;

    private WindowStrategy windowStrategy;

    private Long sentId;

    private Stage stage;

    public void setService(UserService userService, WindowStrategy windowStrategy, User user, Stage stage){
        this.userService = userService;
        this.windowStrategy = windowStrategy;
        sentId = user.getId();
        this.stage = stage;

        if (windowStrategy == WindowStrategy.Update){
            firstnameTextField.setText(user.getFirstname());
            lastnameTextField.setText(user.getLastname());
            passwordTextField.setText(user.getPassword());
        }
    }

    private void signUpSuccessfull(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SIGN UP");
        alert.setContentText("SIGNED UP WITH SUCCES!");
        alert.show();
    }

    public void finishSettingUserAttributes(ActionEvent actionEvent) {
        try{
            String firstname = firstnameTextField.getText();
            String lastname = lastnameTextField.getText();
            String password = passwordTextField.getText();

            switch (windowStrategy){
                case Add -> userService.addUser(firstname, lastname, password);
                case Update -> userService.update(sentId, firstname, lastname, password);
            }
            signUpSuccessfull();
            stage.close();
        }catch (ExistingEntityException | InexistingEntityException | ValidationException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occurred: " + exception.getMessage());
            alert.showAndWait();
        }
    }
}
