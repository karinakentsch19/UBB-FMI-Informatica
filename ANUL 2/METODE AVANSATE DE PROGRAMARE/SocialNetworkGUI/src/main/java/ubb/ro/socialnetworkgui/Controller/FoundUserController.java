package ubb.ro.socialnetworkgui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ubb.ro.socialnetworkgui.Domain.User;

public class FoundUserController {
    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label passwordLabel;

    private User user;

    public void initUser(User user){
        this.user = user;
        setLabels();
    }

    private void setLabels(){
        firstnameLabel.setText(user.getFirstname());
        lastnameLabel.setText(user.getLastname());
        passwordLabel.setText(user.getPassword());
    }

}
