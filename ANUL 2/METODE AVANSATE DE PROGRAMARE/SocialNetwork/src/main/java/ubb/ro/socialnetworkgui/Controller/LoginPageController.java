package ubb.ro.socialnetworkgui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Exceptions.InexistingEntityException;
import ubb.ro.socialnetworkgui.HelloApplication;
import ubb.ro.socialnetworkgui.Service.FriendRequestService;
import ubb.ro.socialnetworkgui.Service.FriendshipService;
import ubb.ro.socialnetworkgui.Service.MessageService;
import ubb.ro.socialnetworkgui.Service.UserService;
import ubb.ro.socialnetworkgui.UsefulTools.WindowStrategy;

import java.io.File;
import java.io.IOException;

public class LoginPageController {

    @FXML
    private TextField loginFirstnameTextField;

    @FXML
    private TextField loginLastnameTextField;

    @FXML
    private TextField loginPasswordTextField;

    private UserService userService;

    private FriendshipService friendshipService;

    private FriendRequestService friendRequestService;

    private MessageService messageService;

    public void setService (UserService userService, FriendshipService friendshipService, FriendRequestService friendRequestService, MessageService messageService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.friendRequestService = friendRequestService;
        this.messageService = messageService;
    }

    public void onPressLoginButton(ActionEvent actionEvent) {
        try {

            String firstName = loginFirstnameTextField.getText();
            String lastName = loginLastnameTextField.getText();
            String password = loginPasswordTextField.getText();
            User user = userService.findUserByFirstnameLastnamePassword(firstName, lastName, password);

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/myUserPage-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 400);
            stage.setTitle(firstName + " " + lastName);
            stage.setScene(scene);

            MyUserPage myUserPage = loader.getController();
            myUserPage.setService(messageService,userService, friendshipService, friendRequestService, user);

            stage.show();

        }catch (IOException | InexistingEntityException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
        finally {
            clearTextfields();
        }
    }

    private void clearTextfields(){
        loginFirstnameTextField.clear();
        loginLastnameTextField.clear();
        loginPasswordTextField.clear();
    }

    public void onPressSignupButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/userattributes-view.fxml"));
        Stage stage = new Stage();

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        UserAttributesController userAttributesController = loader.getController();
        userAttributesController.setService(userService, WindowStrategy.Add, new User("", "", ""), stage);
        stage.show();
    }
}
