package ubb.ro.socialnetworkgui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ubb.ro.socialnetworkgui.DTO.FriendRequestsDTO;
import ubb.ro.socialnetworkgui.DTO.FriendshipDTO;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.Service.FriendRequestService;
import ubb.ro.socialnetworkgui.Service.FriendshipService;
import ubb.ro.socialnetworkgui.Service.MessageService;
import ubb.ro.socialnetworkgui.Service.UserService;
import ubb.ro.socialnetworkgui.UsefulTools.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MyUserPage implements Observer<TargetedWindow> {
    @FXML
    private TableView<FriendshipDTO>  friendsTableView;

    @FXML
    private TableView<FriendRequestsDTO> requestsTableView;

    @FXML
    private TableColumn<FriendshipDTO, Long> myFriendIdTableColumn;

    @FXML
    private TableColumn<FriendshipDTO, String> myFriendNameTableColumn;

    @FXML
    private TableColumn<FriendshipDTO, LocalDateTime> myFriendMeetingDateTableColumn;


    @FXML
    private TableColumn<FriendRequestsDTO, Long> friendrequestIdTableColumn;


    @FXML
    private TableColumn<FriendRequestsDTO, String> friendrequestFirstnameTableColumn;


    @FXML
    private TableColumn<FriendRequestsDTO, String> friendrequestLastnameTableColumn;

    @FXML
    private ChoiceBox<User> friendChoiceBox;

    @FXML
    private ObservableList<FriendshipDTO> friendshipModel = FXCollections.observableArrayList();


    @FXML
    private ObservableList<FriendRequestsDTO> friendRequestsDTOModel = FXCollections.observableArrayList();

    private UserService userService;

    private FriendshipService friendshipService;

    private FriendRequestService friendRequestService;

    private User myUser;

    private int pageSizeFriendRequest = 3;
    private int currentPageFriendRequest = 0;

    private int totalNumberOfElementsFriendRequest = 0;
    private int pageSizeFriends = 3;
    private int currentPageFriends = 0;

    private int totalNumberOfElementsFriends = 0;

    @FXML
    private Button friendshipPreviousButton;

    @FXML
    private Button friendshipNextButton;

    @FXML
    private Button friendrequestPreviousButton;

    @FXML
    private Button friendrequestNextButton;

    private MessageService messageService;

    @FXML
    private TextField myUserPageNumberOfRowsTextfield;

    public void setService(MessageService messageService, UserService userService, FriendshipService friendshipService, FriendRequestService friendRequestService, User myUser) {
        this.userService = userService;
        this.messageService = messageService;
        this.friendshipService = friendshipService;
        this.friendRequestService = friendRequestService;
        this.myUser = myUser;
        initializeFriendsModel();
        initializeFriendsTable();
        initializeFriendRequestsModel();
        initializeFriendRequestsTable();
        initChoiceBox();
        this.userService.addObserver(this);
        this.friendshipService.addObserver(this);
        this.friendRequestService.addObserver(this);

        myUserPageNumberOfRowsTextfield.setText("3");
    }

    private void initializeFriendsTable() {
        myFriendIdTableColumn.setCellValueFactory(new PropertyValueFactory<FriendshipDTO, Long>("id2"));
        myFriendNameTableColumn.setCellValueFactory(new PropertyValueFactory<FriendshipDTO, String>("name"));
        myFriendMeetingDateTableColumn.setCellValueFactory(new PropertyValueFactory<FriendshipDTO, LocalDateTime>("meetingDate"));
        friendsTableView.setItems(friendshipModel);
    }

    private void initializeFriendRequestsTable() {
        friendrequestIdTableColumn.setCellValueFactory(new PropertyValueFactory<FriendRequestsDTO, Long>("id"));
        friendrequestFirstnameTableColumn.setCellValueFactory(new PropertyValueFactory<FriendRequestsDTO, String>("firstName"));
        friendrequestLastnameTableColumn.setCellValueFactory(new PropertyValueFactory<FriendRequestsDTO, String>("lastName"));
        requestsTableView.setItems(friendRequestsDTOModel);
    }
    private void initializeFriendsModel() {
        Page<FriendshipDTO> page = friendshipService.findAllOnPageforUser(new Pageable(currentPageFriends, pageSizeFriends), myUser.getId());

        int maxPage = (int) Math.ceil((double) page.getTotalNumberOfElements() / pageSizeFriends) - 1;
        if (maxPage < 0)
            maxPage = 0;
        if (currentPageFriends > maxPage){
            currentPageFriends = maxPage;
            page = friendshipService.findAllOnPageforUser(new Pageable(currentPageFriends, pageSizeFriends), myUser.getId());
        }

        friendshipModel.setAll(StreamSupport.stream(page.getElementsOnPage().spliterator(),false).collect(Collectors.toList()));

        totalNumberOfElementsFriends = page.getTotalNumberOfElements();

        friendshipPreviousButton.setDisable(currentPageFriends == 0);
        friendshipNextButton.setDisable((currentPageFriends + 1) * pageSizeFriends >= totalNumberOfElementsFriends);
    }

    private void initializeFriendRequestsModel() {
        Page<FriendRequestsDTO> page = friendRequestService.findAllOnPage(new Pageable(currentPageFriendRequest, pageSizeFriendRequest), myUser.getId());

        int maxPage = (int) Math.ceil((double) page.getTotalNumberOfElements() / pageSizeFriendRequest) - 1;
        if (maxPage < 0)
            maxPage = 0;
        if (currentPageFriendRequest > maxPage){
            currentPageFriendRequest = maxPage;
            page = friendRequestService.findAllOnPage(new Pageable(currentPageFriendRequest, pageSizeFriendRequest), myUser.getId());
        }

        friendRequestsDTOModel.setAll(StreamSupport.stream(page.getElementsOnPage().spliterator(),false).collect(Collectors.toList()));

        totalNumberOfElementsFriendRequest = page.getTotalNumberOfElements();

        friendrequestPreviousButton.setDisable(currentPageFriendRequest == 0);
        friendrequestNextButton.setDisable((currentPageFriendRequest + 1) * pageSizeFriendRequest >= totalNumberOfElementsFriendRequest);
    }

    private void initChoiceBox(){
        ObservableList<User> observableList = FXCollections.observableArrayList();
        List<User> userList = StreamSupport.stream(friendRequestService.usersWhoAreNotMyFriends(myUser.getId()).spliterator(), false).toList();
        observableList.setAll(userList);
        friendChoiceBox.setItems(observableList);
        friendChoiceBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                if (object == null)
                    return null;
                else
                    return object.getFirstname() + " " + object.getLastname();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
    }

    public void pressOnFriendRequestPrevButton(ActionEvent actionEvent) {
        currentPageFriendRequest--;
        initializeFriendRequestsModel();
    }

    public void pressOnFriendRequestNextButton(ActionEvent actionEvent) {
        currentPageFriendRequest++;
        initializeFriendRequestsModel();
    }

    public void pressOnPreviousButtonFriendsMyPage(ActionEvent actionEvent) {
        currentPageFriends--;
        initializeFriendsModel();
    }

    public void pressOnNextButtonFriendsMyPage(ActionEvent actionEvent) {
        currentPageFriends++;
        initializeFriendsModel();
    }

    public void pressOnSendRequestButton(ActionEvent actionEvent) {
        User selectedUser = friendChoiceBox.getValue();
        friendRequestService.addFriendRequest(myUser.getId(), selectedUser.getId(), "pending");
    }

    public void pressOnOpenChatWithFriendButton(ActionEvent actionEvent) {
        try {
            File fxmlFile = new File("..\\SocialNetworkGUI\\src\\main\\resources\\ubb\\ro\\socialnetworkgui\\Views\\privateChatWithMyFriends-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            FriendshipDTO friendshipDTO = friendsTableView.getSelectionModel().getSelectedItem();
            User myFriend = userService.find(friendshipDTO.getId2());

            stage.setTitle(myFriend.getFirstname() + " " + myFriend.getLastname());

            PrivateChatWithMyFriendsController privateChatWithMyFriendsController = loader.getController();
            privateChatWithMyFriendsController.initService(messageService,myUser, myFriend);
            stage.show();
        }catch (IOException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    public void pressOnSendMsgToFriendsButton(ActionEvent actionEvent) {
        try {
            File fxmlFile = new File("..\\SocialNetworkGUI\\src\\main\\resources\\ubb\\ro\\socialnetworkgui\\Views\\messageForFriends-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 600, 400);
            stage.setScene(scene);
            stage.setTitle("To my friends");

            MessageForMyFriendsController messageForMyFriendsController = loader.getController();
            List<User> myFriends = StreamSupport.stream(friendshipService.getAllFriendsForAUser(myUser).spliterator(), false).toList();
            messageForMyFriendsController.initService(messageService, myFriends,myUser);
            stage.show();
        }catch (IOException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    public void pressOnAcceptRequestButton(ActionEvent actionEvent) {
        FriendRequestsDTO friendRequestsDTO = requestsTableView.getSelectionModel().getSelectedItem();
        friendRequestService.acceptAFriendRequest(friendRequestsDTO.getId(),myUser.getId());
    }

    public void pressOnDenyRequestButton(ActionEvent actionEvent) {
        FriendRequestsDTO friendRequestsDTO = requestsTableView.getSelectionModel().getSelectedItem();
        friendRequestService.denyAFriendRequest(friendRequestsDTO.getId(),myUser.getId());
    }

    @Override
    public void update(TargetedWindow event) {
        if (Objects.equals(event.getId(), myUser.getId()) ||  event.getId() == null)
            switch (event.getEvent()){
                case users -> initChoiceBox();
                case friendships -> initializeFriendsModel();
                case friendRequests -> {initializeFriendRequestsModel();initChoiceBox();}
            }
    }

    public void pressOnRemoveFriendButton(ActionEvent actionEvent) {
        FriendshipDTO friendshipDTO = friendsTableView.getSelectionModel().getSelectedItem();
        friendshipService.removeFriendship(friendshipDTO.getId2(), myUser.getId());
        friendRequestService.delete(friendshipDTO.getId2(), myUser.getId());
        friendRequestService.delete(myUser.getId(), friendshipDTO.getId2());
    }


    public void myUserPageNoOfRowsChangedTextField(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            pageSizeFriends = Integer.parseInt(myUserPageNumberOfRowsTextfield.getText());
            pageSizeFriendRequest = Integer.parseInt(myUserPageNumberOfRowsTextfield.getText());
            initializeFriendsModel();
            initializeFriendRequestsModel();
        }
    }
}
