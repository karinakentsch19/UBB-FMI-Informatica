package ubb.ro.socialnetworkgui.Repository;

import ubb.ro.socialnetworkgui.Domain.Message;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Validator.MessageValidator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.LoggingPermission;

public class MessageDBRepository implements AbstractRepository<Long, Message> {

    private String url;
    private String username;
    private String password;

    private MessageValidator messageValidator;

    public MessageDBRepository(String url, String username, String password, MessageValidator messageValidator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.messageValidator = messageValidator;
    }

    private Long getBiggestId() {
        long maximumId = 0L;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select max(idMessage) as max from messages");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                maximumId = resultSet.getLong("max");
            return maximumId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> add(Message entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into messages(idFrom, msg, senddate, idReplyMsg) values (?,?,?,?)");
        ) {
            messageValidator.validate(entity);
            preparedStatement.setLong(1, entity.getFromUser());
            preparedStatement.setString(2, entity.getMessage());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));

            if (entity.getReplyMessageId() == null)
                preparedStatement.setNull(4, Types.INTEGER);
            else
                preparedStatement.setLong(4, entity.getReplyMessageId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.of(entity);


            try (
                    PreparedStatement statement = connection.prepareStatement("insert into receivers(idTo, idMessage) values (?, ?)");
            ) {
                statement.setLong(2, getBiggestId());
                for (Long idTo : entity.getToUsers()) {
                    statement.setLong(1, idTo);
                    if (statement.executeUpdate() == 0)
                        return Optional.of(entity);
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> remove(Long id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("delete from messages where idMessage = ?");
        ) {
            preparedStatement.setLong(1, id);
            Optional<Message> message = find(id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.empty();
            else
                return message;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> update(Message entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update messages set msg = ? where idMessage = ?");
        ) {
            messageValidator.validate(entity);
            preparedStatement.setString(1, entity.getMessage());
            preparedStatement.setLong(2, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.of(entity);
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Long> getAllReceivers(Long idMessage) {
        List<Long> receivers = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select idTo from receivers where idMessage = ?");
        ) {
            preparedStatement.setLong(1, idMessage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idTo = resultSet.getLong("idTo");
                receivers.add(idTo);
            }
            return receivers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Message> getAll() {
        List<Message> messageList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from messages");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idFrom = resultSet.getLong("idFrom");
                String msg = resultSet.getString("msg");
                LocalDateTime date = resultSet.getTimestamp("senddate").toLocalDateTime();
                Long idReplyMsg = resultSet.getLong("idReplyMsg");
                if (resultSet.wasNull())
                    idReplyMsg = null;
                Long id = resultSet.getLong("idMessage");
                Message message = new Message(idFrom, getAllReceivers(id), msg, idReplyMsg, date);
                message.setId(id);

                messageList.add(message);
            }
            return messageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> find(Long id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from messages where idMessage = ?");
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long idFrom = resultSet.getLong("idFrom");
                String msg = resultSet.getString("msg");
                LocalDateTime date = resultSet.getTimestamp("senddate").toLocalDateTime();
                Long idReplyMsg = resultSet.getLong("idReplyMsg");

                if (resultSet.wasNull())
                    idReplyMsg = null;

                Message message = new Message(idFrom, getAllReceivers(id), msg, idReplyMsg, date);
                message.setId(id);

                return Optional.of(message);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long size() {
        Long numberOfMessages = 0L;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from messages");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfMessages = resultSet.getLong(1);
            }
            return numberOfMessages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Message> getMessagesOFTwoUsers(Long fromUser, Long toUser) {
        List<Message> messageList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select Msg.*, R.idTo\n" +
                        "from messages Msg\n" +
                        "join receivers R on Msg.idMessage = R.idMessage\n" +
                        "where (Msg.idFrom = ? and R.idTo = ?) or (Msg.idFrom = ? and R.idTo = ?)\n" +
                        "order by Msg.senddate");
        ) {

            preparedStatement.setLong(1, fromUser);
            preparedStatement.setLong(2, toUser);
            preparedStatement.setLong(3, toUser);
            preparedStatement.setLong(4, fromUser);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idFrom = resultSet.getLong("idFrom");
                String msg = resultSet.getString("msg");
                LocalDateTime date = resultSet.getTimestamp("senddate").toLocalDateTime();
                Long idRepliedMsg = resultSet.getLong("idReplyMsg");
                if (resultSet.wasNull())
                    idRepliedMsg = null;
                Long id = resultSet.getLong("idMessage");
                Long idTo = resultSet.getLong("idTo");

                Message message = new Message(idFrom, List.of(idTo), msg, idRepliedMsg, date);
                message.setId(id);

                messageList.add(message);
            }
            return messageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Message> getMessagesOFTwoUsersByDate(Long fromUser, Long toUser, LocalDateTime date) {
        List<Message> messageList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select Msg.*, R.idTo\n" +
                        "from messages Msg\n" +
                        "join receivers R on Msg.idMessage = R.idMessage\n" +
                        "where ((Msg.idFrom = ? and R.idTo = ?) or (Msg.idFrom = ? and R.idTo = ?)) and Msg.senddate > ?\n" +
                        "order by Msg.senddate");
        ) {

            preparedStatement.setLong(1, fromUser);
            preparedStatement.setLong(2, toUser);
            preparedStatement.setLong(3, toUser);
            preparedStatement.setLong(4, fromUser);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(date));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idFrom = resultSet.getLong("idFrom");
                String msg = resultSet.getString("msg");
                LocalDateTime date1 = resultSet.getTimestamp("senddate").toLocalDateTime();
                Long idRepliedMsg = resultSet.getLong("idReplyMsg");
                if (resultSet.wasNull())
                    idRepliedMsg = null;
                Long id = resultSet.getLong("idMessage");
                Long idTo = resultSet.getLong("idTo");

                Message message = new Message(idFrom, List.of(idTo), msg, idRepliedMsg, date1);
                message.setId(id);

                messageList.add(message);
            }
            return messageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
