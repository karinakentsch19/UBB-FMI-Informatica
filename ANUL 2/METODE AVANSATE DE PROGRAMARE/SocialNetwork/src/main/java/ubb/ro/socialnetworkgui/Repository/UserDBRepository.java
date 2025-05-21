package ubb.ro.socialnetworkgui.Repository;

import ubb.ro.socialnetworkgui.Domain.User;
import ubb.ro.socialnetworkgui.Repository.Paging.Page;
import ubb.ro.socialnetworkgui.Repository.Paging.Pageable;
import ubb.ro.socialnetworkgui.Repository.Paging.PagingRepository;
import ubb.ro.socialnetworkgui.UsefulTools.PasswordEncryptor;
import ubb.ro.socialnetworkgui.Validator.AbstractValidator;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDBRepository implements PagingRepository<Long, User> {

    private String url;
    private String username;
    private String password;
    AbstractValidator<User> validator;
    private byte[] salt;

    public UserDBRepository(String url, String username, String password, AbstractValidator<User> validator, byte[] salt) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        this.salt = salt;
    }

    @Override
    public Optional<User> add(User entity) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id, firstname, lastname, password) values (?,?,?,?)");
        ) {
            validator.validate(entity);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getFirstname());
            preparedStatement.setString(3, entity.getLastname());
            preparedStatement.setString(4, PasswordEncryptor.hashPassword(entity.getPassword(), salt));
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
    public Optional<User> remove(Long id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?");
        ) {
            preparedStatement.setLong(1, id);
            Optional<User> user = find(id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.empty();
            else
                return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> update(User user) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("update users set firstname = ?, lastname = ?, password = ? where id = ?");
        ) {
            validator.validate(user);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, PasswordEncryptor.hashPassword(user.getPassword(), salt));
            preparedStatement.setLong(4, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                return Optional.of(user);
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");
                Long id = resultSet.getLong("id");
                User user = new User(firstname, lastname, password);
                user.setId(id);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> find(Long id) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");

                User user = new User(firstname, lastname, password);
                user.setId(id);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findUserByFirstnameLastnamePassword(String firstname, String lastname, String psw) {
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users where firstname = ? and lastname = ?");
        ) {
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
//            preparedStatement.setString(3, PasswordEncryptor.hashPassword(psw, salt));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String Firstname = resultSet.getString("firstname");
                String Lastname = resultSet.getString("lastname");
                String Password = resultSet.getString("password");
                Long id = resultSet.getLong("id");
                User user = new User(Firstname, Lastname, Password);
                user.setId(id);
                if (PasswordEncryptor.verifyPassword(psw, Password, salt))
                    return Optional.of(user);
                else
                    return Optional.empty();
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Long size() {
        Long numberOfUsers = 0L;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from users");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfUsers = resultSet.getLong(1);
            }
            return numberOfUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<User> findAllOnPage(Pageable pageable) {
        List<User> users = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pageStatement = connection.prepareStatement("select * from users limit ? offset ?");

        ){
            pageStatement.setInt(1, pageable.getPageSize());
            pageStatement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());

            ResultSet resultSet = pageStatement.executeQuery();

            while (resultSet.next()){
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");
                Long id = resultSet.getLong("id");

                User user = new User(firstname, lastname, password);
                user.setId(id);

                users.add(user);
            }
            return new Page<>(users, Math.toIntExact(size()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
