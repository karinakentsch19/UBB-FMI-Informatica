package ubb.ro.socialnetworkgui.Repository;


import ubb.ro.socialnetworkgui.Domain.FriendRequest;
import ubb.ro.socialnetworkgui.Domain.Friendship;
import ubb.ro.socialnetworkgui.Domain.Pair;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.Repository.Paging.PagingRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipDBRepository implements PagingRepository<Pair<Long, Long>, Friendship> {

    private String url;
    private String username;
    private String password;

    public FriendshipDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Friendship> add(Friendship entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into friendships(id1, id2, meetingdate) values (?,?,?)");
        ) {
            preparedStatement.setLong(1, entity.getUser1());
            preparedStatement.setLong(2, entity.getUser2());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getMeetingDate()));
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.empty();
            else
                return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> remove(Pair<Long, Long> id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("delete from friendships where id1 = ? and id2 = ?");
        ) {
            preparedStatement.setLong(1, id.getElem1());
            preparedStatement.setLong(2, id.getElem2());
            Optional<Friendship> friendship = find(id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.empty();
            else
                return friendship;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update friendships set meetingdate = ? where id1 = ? and id2 = ?");
        ) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(entity.getMeetingDate()));
            preparedStatement.setLong(2, entity.getUser1());
            preparedStatement.setLong(3, entity.getUser2());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.of(entity);
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Friendship> getAll() {
        List<Friendship> friendshipList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from friendships");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDateTime meetingdate = resultSet.getTimestamp("meetingdate").toLocalDateTime();
                Friendship friendship = new Friendship(id1, id2, meetingdate);
                friendship.setId(new Pair<Long, Long>(id1, id2));
                friendshipList.add(friendship);
            }
            return friendshipList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> find(Pair<Long, Long> id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from friendships where id1 = ? and id2 = ?");
        ) {
            preparedStatement.setLong(1, id.getElem1());
            preparedStatement.setLong(2, id.getElem2());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDateTime meetingdate = resultSet.getTimestamp("meetingdate").toLocalDateTime();
                Friendship friendship = new Friendship(id1, id2, meetingdate);
                friendship.setId(new Pair<Long, Long>(id1, id2));
                return Optional.of(friendship);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long size() {
        Long numberOfFriendships = 0L;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from friendships");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfFriendships = resultSet.getLong(1);
            }
            return numberOfFriendships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Friendship> findAllOnPage(Pageable pageable) {
        List<Friendship> friendships = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pageStatement = connection.prepareStatement("select * from friendships limit ? offset ?");

        ) {
            pageStatement.setInt(1, pageable.getPageSize());
            pageStatement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());

            ResultSet resultSet = pageStatement.executeQuery();

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDateTime meetingdate = resultSet.getTimestamp("meetingdate").toLocalDateTime();
                Friendship friendship = new Friendship(id1, id2, meetingdate);
                friendship.setId(new Pair<Long, Long>(id1, id2));
                friendships.add(friendship);
            }
            return new Page<>(friendships, Math.toIntExact(size()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Page<Friendship> findAllOnPageForUser(Pageable pageable, Long userId) {
        List<Friendship> friendships = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pageStatement = connection.prepareStatement("select * from friendships where id1 = ? limit ? offset ?");
             PreparedStatement countStatement = connection.prepareStatement("select count(*) from friendships where id1 = ?");
        ) {
            pageStatement.setLong(1, userId);
            pageStatement.setInt(2, pageable.getPageSize());
            pageStatement.setInt(3, pageable.getPageSize() * pageable.getPageNumber());

            countStatement.setLong(1, userId);

            ResultSet resultSet = pageStatement.executeQuery();
            ResultSet resultSetCount = countStatement.executeQuery();

            int count = 0;
            if (resultSetCount.next())
                count = resultSetCount.getInt(1);

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDateTime meetingdate = resultSet.getTimestamp("meetingdate").toLocalDateTime();
                Friendship friendship = new Friendship(id1, id2, meetingdate);
                friendship.setId(new Pair<Long, Long>(id1, id2));
                friendships.add(friendship);
            }
            return new Page<>(friendships, count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

