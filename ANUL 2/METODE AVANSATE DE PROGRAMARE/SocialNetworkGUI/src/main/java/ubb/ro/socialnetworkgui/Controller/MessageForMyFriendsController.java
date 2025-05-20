package ubb.ro.socialnetworkgui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ubb.ro.socialnetworkgui.Domain.Friendship;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Exceptions.ValidationException;
import ubb.ro.socialnetworkgui.Service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

public class MessageForMyFriendsController {
    @FXML
    private TextField sendMessageToMyFriendsTextfield;

    MessageService messageService;

    List<User> myFriends;

    User myUser;

    public void initService(MessageService messageService, List<User> friendships, User user) {
        this.messageService = messageService;
        this.myFriends = friendships;
        this.myUser = user;
    }

    public void pressOnSendMsgToMyFriendsButton(ActionEvent actionEvent) {
        try {
            String message = sendMessageToMyFriendsTextfield.getText();
            messageService.sendMessageToFriends(myUser.getId(), myFriends, message, LocalDateTime.now(), null);
        } catch (ValidationException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
        sendMessageToMyFriendsTextfield.clear();
    }
}
