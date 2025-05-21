package ubb.ro.socialnetworkgui.Service;


import ubb.ro.socialnetworkgui.Domain.Entity;
import ubb.ro.socialnetworkgui.Domain.Message;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Repository.MessageDBRepository;
import ubb.ro.socialnetworkgui.UsefulTools.EntitiesThatChange;
import ubb.ro.socialnetworkgui.UsefulTools.TargetedWindow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class MessageService extends AbstractService {
    private MessageDBRepository messages;

    public MessageService(MessageDBRepository messages) {
        super(new ArrayList<>());
        this.messages = messages;
    }

    public void addMessage(Long fromUser, Long toUser, String msg, LocalDateTime date, Long repliedMessage) {
        Message message = new Message(fromUser, List.of(toUser), msg, repliedMessage, date);
        messages.add(message);

        notifyAllObservers(new TargetedWindow(fromUser, EntitiesThatChange.messages));
        notifyAllObservers(new TargetedWindow(toUser, EntitiesThatChange.messages));
    }

    public void sendMessageToFriends(Long fromUser, List<User> toFriends, String msg, LocalDateTime date, Long repliedMessage) {
        Function<User, Long> userToId = Entity::getId;

        Message message = new Message(fromUser,toFriends.stream().map(userToId).toList(), msg, repliedMessage, date);
        messages.add(message);

        toFriends.stream().map(userToId).forEach(id -> notifyAllObservers(new TargetedWindow(id, EntitiesThatChange.messages)));
        notifyAllObservers(new TargetedWindow(fromUser, EntitiesThatChange.messages));
    }

    public void deleteMessage(Long id) {
        Message message = messages.remove(id).get();
        notifyAllObservers(new TargetedWindow(message.getFromUser(), EntitiesThatChange.messages));

        message.getToUsers().forEach(idUser -> notifyAllObservers(new TargetedWindow(idUser, EntitiesThatChange.messages)));
    }

    public Message findMessage(Long id) {
        return messages.find(id).get();
    }

    public Iterable<Message> getAllMessagesForTwoUsers(Long fromUser, Long toUser, LocalDateTime date) {

        if (date == null){
            return StreamSupport.stream(messages.getMessagesOFTwoUsers(fromUser, toUser).spliterator(), false).toList();
        }
        else {
            return StreamSupport.stream(messages.getMessagesOFTwoUsersByDate(fromUser, toUser, date).spliterator(), false).toList();
        }
    }

}
