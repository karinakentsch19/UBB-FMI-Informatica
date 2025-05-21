package ubb.ro.socialnetworkgui.Repository;

import ubb.ro.socialnetworkgui.Domain.FriendRequest;
import ubb.ro.socialnetworkgui.Domain.Friendship;
import ubb.ro.socialnetworkgui.Domain.Pair;
import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.Repository.Paging.PagingRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipRequestDBRepository implements PagingRepository<Pair<Long, Long>, FriendRequest> {


    private String url;
    private String username;
    private String password;

    public FriendshipRequestDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<FriendRequest> add(FriendRequest entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into friendrequests(id1, id2, status) values (?,?,?)");
        ) {
            preparedStatement.setLong(1, entity.getIdUserFrom());
            preparedStatement.setLong(2, entity.getIdUserTo());
            preparedStatement.setString(3, entity.getStatus());
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
    public Optional<FriendRequest> remove(Pair<Long, Long> id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("delete from friendrequests where id1 = ? and id2 = ?");
        ) {
            preparedStatement.setLong(1, id.getElem1());
            preparedStatement.setLong(2, id.getElem2());
            Optional<FriendRequest> friendRequest = find(id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.empty();
            else
                return friendRequest;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<FriendRequest> update(FriendRequest entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update friendrequests set status = ? where id1 = ? and id2 = ?");
        ) {
            preparedStatement.setString(1, entity.getStatus());
            preparedStatement.setLong(2, entity.getIdUserFrom());
            preparedStatement.setLong(3, entity.getIdUserTo());
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
    public Iterable<FriendRequest> getAll() {
        List<FriendRequest> friendRequestList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from friendrequests");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String status = resultSet.getString("status");
                FriendRequest friendRequest = new FriendRequest(id1, id2, status);
                friendRequest.setId(new Pair<Long, Long>(id1, id2));
                friendRequestList.add(friendRequest);
            }
            return friendRequestList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<FriendRequest> find(Pair<Long, Long> id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from friendrequests where id1 = ? and id2 = ?");
        ) {
            preparedStatement.setLong(1, id.getElem1());
            preparedStatement.setLong(2, id.getElem2());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String status = resultSet.getString("status");
                FriendRequest friendRequest = new FriendRequest(id1, id2, status);
                friendRequest.setId(new Pair<Long, Long>(id1, id2));
                return Optional.of(friendRequest);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long size() {
        Long numberOfFriendRequests = 0L;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from friendrequests");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfFriendRequests = resultSet.getLong(1);
            }
            return numberOfFriendRequests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<FriendRequest> findAllOnPage(Pageable pageable) {
        List<FriendRequest> friendRequests = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pageStatement = connection.prepareStatement("select * from friendrequests limit ? offset ?");
        ) {
            pageStatement.setInt(1, pageable.getPageSize());
            pageStatement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());

            ResultSet resultSet = pageStatement.executeQuery();

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String status = resultSet.getString("status");

                FriendRequest friendRequest = new FriendRequest(id1, id2, status);
                friendRequest.setId(new Pair<Long, Long>(id1, id2));
                friendRequests.add(friendRequest);
            }
            return new Page<>(friendRequests, Math.toIntExact(size()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<FriendRequest> getAllRequestForAUserWithGivenStatus(Pageable pageable,Long id, String status){
        List<FriendRequest> friendshipRequestList = new ArrayList<>();
        try(
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement selectStatement = connection.prepareStatement("select * from friendrequests where id2 = ? and status = ? limit ? offset ?");
                PreparedStatement countStatement = connection.prepareStatement("select count(*) from friendrequests where id2 = ? and status = ?");
        ){

            countStatement.setLong(1, id);
            countStatement.setString(2, status);

            selectStatement.setLong(1, id);
            selectStatement.setString(2, status);
            selectStatement.setInt(3,pageable.getPageSize());
            selectStatement.setInt(4,pageable.getPageSize()*pageable.getPageNumber());

            ResultSet resultSet = selectStatement.executeQuery();
            ResultSet countResultSet = countStatement.executeQuery();

            int count = 0;
            if (countResultSet.next())
                count = countResultSet.getInt(1);

            while(resultSet.next()){
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String statusFriendshipRequest = resultSet.getString("status");
                FriendRequest friendRequest = new FriendRequest(id1, id2, statusFriendshipRequest);
                friendshipRequestList.add(friendRequest);
            }
            return new Page<>(friendshipRequestList,count);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
