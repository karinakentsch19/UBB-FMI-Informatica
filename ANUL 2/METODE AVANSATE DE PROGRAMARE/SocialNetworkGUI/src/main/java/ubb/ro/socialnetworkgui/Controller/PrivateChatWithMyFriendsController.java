package ubb.ro.socialnetworkgui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ubb.ro.socialnetworkgui.Domain.Message;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Service.MessageService;
import ubb.ro.socialnetworkgui.UsefulTools.Observer;
import ubb.ro.socialnetworkgui.UsefulTools.TargetedWindow;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class PrivateChatWithMyFriendsController implements Observer<TargetedWindow> {
    @FXML
    private TextField privateChatTextField;

    @FXML
    private ScrollPane privateChatScrollPane;

    @FXML
    private VBox vBoxForScrollPane;

    private MessageService messageService;

    private User myUser;

    private User myFriend;

    @FXML
    private Label replyMessage = null;

    private LocalDateTime lastMessageDate = null;

    public void initService(MessageService messageService, User myUser, User myFriend){
        this.messageService = messageService;
        this.myFriend = myFriend;
        this.myUser = myUser;

        vBoxForScrollPane = new VBox();

        privateChatScrollPane.setContent(vBoxForScrollPane);
        vBoxForScrollPane.setPrefWidth(privateChatScrollPane.getPrefWidth() - 10);
        privateChatScrollPane.vvalueProperty().bind(vBoxForScrollPane.heightProperty());
        messageService.addObserver(this);
        initChat();
    }

    public void initChat(){
        Iterable<Message> messages = messageService.getAllMessagesForTwoUsers(myUser.getId(), myFriend.getId(), lastMessageDate);

        for (Message message: messages){

            Label text = new Label(message.getMessage());
            text.setId(message.getId().toString());
            text.setOnMouseClicked(event -> selectedReplyMessage(text));

            text.setFont(new Font(18));

//            text.setStyle("-fx-padding: 10px; -fx-background-color: grey; -fx-text-fill: black;");

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(10));

            lastMessageDate = message.getDate();
            if (message.getReplyMessageId() != null) {

                VBox vBox = new VBox();

                Label replyMsg = new Label(messageService.findMessage(message.getReplyMessageId()).getMessage());

                vBox.getChildren().add(replyMsg);
                vBox.getChildren().add(text);

                hBox.getChildren().add(vBox);
                hBox.setPrefWidth(vBoxForScrollPane.getPrefWidth());

                if (Objects.equals(message.getFromUser(), myUser.getId())) {
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    vBox.setAlignment(Pos.CENTER_RIGHT);
                    replyMsg.setStyle("-fx-padding: 10px; -fx-border-color: grey; -fx-text-fill: grey;");
                    text.setStyle("-fx-padding: 10px; -fx-border-color: black; -fx-text-fill: black;");
                } else {
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    vBox.setAlignment(Pos.CENTER_LEFT);
                    replyMsg.setStyle("-fx-padding: 10px; -fx-border-color: grey; -fx-text-fill: grey;");
                    text.setStyle("-fx-padding: 10px; -fx-border-color: blue; -fx-text-fill: black;");
                }

                vBoxForScrollPane.getChildren().add(hBox);
            }
            else{
                hBox.getChildren().add(text);
                hBox.setPrefWidth(vBoxForScrollPane.getPrefWidth());

                if (Objects.equals(message.getFromUser(), myUser.getId())) {
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    text.setStyle("-fx-padding: 10px; -fx-border-color: black; -fx-text-fill: black;");
                } else {
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    text.setStyle("-fx-padding: 10px; -fx-border-color: blue; -fx-text-fill: black;");
                }

                vBoxForScrollPane.getChildren().add(hBox);
            }
        }
    }

    public void pressOnSendMsgPrivateChatButton(ActionEvent actionEvent) {
        String message = privateChatTextField.getText();
        Long idReplyMessage;

        if (replyMessage == null)
            idReplyMessage = null;
        else
            idReplyMessage = Long.valueOf(replyMessage.getId());

        messageService.addMessage(myUser.getId(), myFriend.getId(), message, LocalDateTime.now(), idReplyMessage);
        privateChatTextField.clear();
        replyMessage = null;
    }

    @Override
    public void update(TargetedWindow event) {
        if (Objects.equals(event.getId(), myUser.getId()))
            switch (event.getEvent()){
                case messages -> initChat();
            }
    }

    public void selectedReplyMessage(Label label){
        replyMessage = new Label(label.getText());
        replyMessage.setId(label.getId());
    }
}
