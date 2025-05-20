package ubb.ro.socialnetworkgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ubb.ro.socialnetworkgui.Controller.LoginPageController;
import ubb.ro.socialnetworkgui.Controller.UserAttributesController;
import ubb.ro.socialnetworkgui.Controller.UserController;
import ubb.ro.socialnetworkgui.Domain.*;
import ubb.ro.socialnetworkgui.Repository.FriendshipDBRepository;
import ubb.ro.socialnetworkgui.Repository.FriendshipRequestDBRepository;
import ubb.ro.socialnetworkgui.Repository.MessageDBRepository;
import ubb.ro.socialnetworkgui.Repository.Paging.PagingRepository;
import ubb.ro.socialnetworkgui.Repository.UserDBRepository;
import ubb.ro.socialnetworkgui.Service.FriendRequestService;
import ubb.ro.socialnetworkgui.Service.FriendshipService;
import ubb.ro.socialnetworkgui.Service.MessageService;
import ubb.ro.socialnetworkgui.Service.UserService;
import ubb.ro.socialnetworkgui.UsefulTools.PasswordEncryptor;
import ubb.ro.socialnetworkgui.UsefulTools.WindowStrategy;
import ubb.ro.socialnetworkgui.Validator.AbstractValidator;
import ubb.ro.socialnetworkgui.Validator.MessageValidator;
import ubb.ro.socialnetworkgui.Validator.UserValidator;

import java.io.IOException;
import java.util.Arrays;

public class StartApplication extends Application {
    private static final byte[] salt = { 27, -29, 74, -20, -86, -106, 114, -115, -43, 30, -66, 5, 87, 46, 48, -57 };

    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/socialnetwork";
        String username = "postgres";
        String password = "karina19";
//        System.out.println(Arrays.toString(PasswordEncryptor.generateSalt()));

        AbstractValidator<User> userAbstractValidator = new UserValidator();
        MessageValidator messageAbstractValidator = new MessageValidator();

        UserDBRepository userDBRepository = new UserDBRepository(url, username, password, userAbstractValidator, salt);
        FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository(url, username, password);
        FriendshipRequestDBRepository friendRequestDBRepository = new FriendshipRequestDBRepository(url, username, password);
        MessageDBRepository messageDBRepository = new MessageDBRepository(url, username, password, messageAbstractValidator);

        UserService userService = new UserService(userDBRepository);
        FriendshipService friendshipService = new FriendshipService(friendshipDBRepository,userDBRepository);
        FriendRequestService friendRequestService = new FriendRequestService(friendRequestDBRepository, friendshipDBRepository, userDBRepository);
        MessageService messageService = new MessageService(messageDBRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/loginPage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setScene(scene);
        stage.setTitle("Social Network");

        LoginPageController loginPageController = fxmlLoader.getController();
        loginPageController.setService(userService, friendshipService, friendRequestService, messageService);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
