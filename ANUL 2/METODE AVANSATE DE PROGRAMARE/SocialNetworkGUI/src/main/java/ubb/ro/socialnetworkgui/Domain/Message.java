package ubb.ro.socialnetworkgui.Domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Message extends Entity<Long>{
    private Long fromUser;
    private List<Long> toUsers;

    private String message;

    private Long replyMessageId;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime date;

    public Message(Long fromUser, List<Long> toUsers, String message, Long replyMessageId, LocalDateTime date) {
        this.fromUser = fromUser;
        this.toUsers = toUsers;
        this.message = message;
        this.replyMessageId = replyMessageId;
        this.date = date;
    }

    public Long getFromUser() {
        return fromUser;
    }

    public void setFromUser(Long fromUser) {
        this.fromUser = fromUser;
    }

    public List<Long> getToUsers() {
        return toUsers;
    }

    public void setToUsers(List<Long> toUsers) {
        this.toUsers = toUsers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getReplyMessageId() {
        return replyMessageId;
    }

    public void setReplyMessageId(Long replyMessageId) {
        this.replyMessageId = replyMessageId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(fromUser, message1.fromUser) && Objects.equals(toUsers, message1.toUsers) && Objects.equals(message, message1.message) && Objects.equals(replyMessageId, message1.replyMessageId) && Objects.equals(date, message1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUser, toUsers, message, replyMessageId, date);
    }

    @Override
    public String toString() {
        return "Message{" +
                "fromUser=" + fromUser +
                ", toUsers=" + toUsers +
                ", message='" + message + '\'' +
                ", replyMessageId=" + replyMessageId +
                ", date=" + date +
                '}';
    }
}
