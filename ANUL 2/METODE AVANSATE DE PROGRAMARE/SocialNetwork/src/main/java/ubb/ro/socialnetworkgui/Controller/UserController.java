package ubb.ro.socialnetworkgui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Exceptions.ExistingEntityException;
import ubb.ro.socialnetworkgui.Exceptions.InexistingEntityException;
import ubb.ro.socialnetworkgui.Exceptions.ValidationException;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.Service.FriendRequestService;
import ubb.ro.socialnetworkgui.Service.FriendshipService;
import ubb.ro.socialnetworkgui.Service.MessageService;
import ubb.ro.socialnetworkgui.Service.UserService;
import ubb.ro.socialnetworkgui.UsefulTools.EntitiesThatChange;
import ubb.ro.socialnetworkgui.UsefulTools.Observer;
import ubb.ro.socialnetworkgui.UsefulTools.TargetedWindow;
import ubb.ro.socialnetworkgui.UsefulTools.WindowStrategy;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements Observer<TargetedWindow> {

    @FXML
    private TableColumn<User, Long> IdColumn;
    @FXML
    private TableColumn<User, String> FirstnameColumn;
    @FXML
    private TableColumn<User, String> LastnameColumn;
    @FXML
    private TableColumn<User, String> PasswordColumn;
    @FXML
    private TextField UserIdTextField;

    @FXML
    private TableView<User> UsersTable;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    private int pageSize = 5;
    private int currentPage = 0;

    private int totalNumberOfElements = 0;

    private UserService userService;

    private FriendshipService friendshipService;

    private FriendRequestService friendRequestService;

    private MessageService messageService;

    private ObservableList<User> userModel = FXCollections.observableArrayList();

    public void setService(MessageService messageService, UserService userService, FriendshipService friendshipService, FriendRequestService friendRequestService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.friendRequestService = friendRequestService;
        this.messageService = messageService;
        initializeModel();
        initializeTable();
        userService.addObserver(this);
    }

    private void initializeTable() {
        IdColumn.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        FirstnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        LastnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        UsersTable.setItems(userModel);
    }

    private void initializeModel() {
        Page<User> page = userService.findAllOnPage(new Pageable(currentPage, pageSize));

        int maxPage = (int) Math.ceil((double) page.getTotalNumberOfElements() / pageSize) - 1;
        if (maxPage < 0)
            maxPage = 0;
        if (currentPage > maxPage){
            currentPage = maxPage;
            page = userService.findAllOnPage(new Pageable(currentPage, pageSize));
        }

//        List<User> userList = StreamSupport.stream(userService.getAllUsers().spliterator(), false).toList();
//        userModel.setAll(userList);

        userModel.setAll(StreamSupport.stream(page.getElementsOnPage().spliterator(),false).collect(Collectors.toList()));

        totalNumberOfElements = page.getTotalNumberOfElements();

        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage + 1) * pageSize >= totalNumberOfElements);
    }

    @Override
    public void update(TargetedWindow event) {
        if (event.getId() == null)
            switch (event.getEvent()){
                case users -> initializeModel();
                case friendships -> {}
                case friendRequests -> {}
            }
    }

    public void addUserGui(ActionEvent actionEvent) throws IOException {

        File fxmlFile = new File("C:\\Karina - Synced\\UBB KENTSCH KARINA\\ANUL II\\SEMESTRUL 1\\METODE AVANSATE DE PROGRAMARE\\SocialNetworkGUI\\src\\main\\resources\\ubb\\ro\\socialnetworkgui\\Views\\userattributes-view.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());

        Stage stage = new Stage();

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        UserAttributesController userAttributesController = loader.getController();
        userAttributesController.setService(userService, WindowStrategy.Add, new User("", "", ""), stage);
        stage.show();
    }

    public void deleteUserGui(ActionEvent actionEvent) {
        try {
            User user = UsersTable.getSelectionModel().getSelectedItem();
            userService.removeUser(user.getId());
        } catch (InexistingEntityException | NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    public void updateUserGui(ActionEvent actionEvent) throws IOException {
        try {
            File fxmlFile = new File("..\\SocialNetworkGUI\\src\\main\\resources\\ubb\\ro\\socialnetworkgui\\Views\\userattributes-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            UserAttributesController userAttributesController = loader.getController();
            User user = UsersTable.getSelectionModel().getSelectedItem();
            userAttributesController.setService(userService, WindowStrategy.Update, user, stage);
            stage.show();
        }catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    public void findUserGui(ActionEvent actionEvent) throws IOException {
        try {
            Function<String, Long> stringToLong = Long::parseLong;
            Long id = UserIdTextField.getText().transform(stringToLong);

            File fxmlFile = new File("..\\SocialNetworkGUI\\src\\main\\resources\\ubb\\ro\\socialnetworkgui\\Views\\foundUser-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());

            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 300, 200);
            stage.setTitle("Found User");
            stage.setScene(scene);

            FoundUserController foundUserController = loader.getController();

            User user = userService.find(id);
            foundUserController.initUser(user);
            stage.show();
        } catch (ExistingEntityException | InexistingEntityException | ValidationException |
                 NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
        UserIdTextField.clear();
    }

    public void pressOnPreviousButton(ActionEvent actionEvent) {
        currentPage--;
        initializeModel();
    }

    public void pressOnNextButton(ActionEvent actionEvent) {
        currentPage++;
        initializeModel();
    }

    public void enterMyPageButtonAction(ActionEvent actionEvent) {
        try {
            User user = UsersTable.getSelectionModel().getSelectedItem();
            File fxmlFile = new File("..\\SocialNetworkGUI\\src\\main\\resources\\ubb\\ro\\socialnetworkgui\\Views\\myUserPage-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());

            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 700, 400);
            stage.setTitle(user.getFirstname() + " " + user.getLastname());
            stage.setScene(scene);

            MyUserPage myUserPage = loader.getController();
            myUserPage.setService(messageService,userService, friendshipService, friendRequestService, user);

            stage.show();
        }catch (IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("An error occured: " + exception.getMessage());
                alert.showAndWait();
            }
    }
}
