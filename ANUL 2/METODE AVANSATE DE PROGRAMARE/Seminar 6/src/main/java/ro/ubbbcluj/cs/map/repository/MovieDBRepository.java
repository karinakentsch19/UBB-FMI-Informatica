package ro.ubbbcluj.cs.map.repository;

import ro.ubbbcluj.cs.map.domain.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDBRepository implements Repository<Long, Movie> {

    private String url;
    private String username;

    private String password;

    public MovieDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Movie> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement("select * from movies where id = ?");
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                Integer year = resultSet.getInt("year");

                Movie movie = new Movie(title, director, year);
                movie.setId(id);
                return Optional.ofNullable(movie);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Movie> findAll() {
        List<Movie> movieList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement("select * from movies");
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                Integer year = resultSet.getInt("year");
                Movie movie = new Movie(title, director, year);
                movie.setId(id);
                movieList.add(movie);
            }
            return movieList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> save(Movie entity) {
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement("insert into movies(title, director, year) values (?,?,?)");
        ) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDirector());
            preparedStatement.setInt(3, entity.getYear());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0)
                return Optional.empty();
            else
                return Optional.ofNullable(entity);
//            return affectedRows == 0 ? Optional.empty() : Optional.ofNullable(entity);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> delete(Long id) {
        int affectedRows = 0;
        Optional<Movie> movie = this.findOne(id);
        if (movie.isPresent()) {
            try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
                 PreparedStatement preparedStatement = connection.prepareStatement("delete from movies where id = ?");
            ) {
                preparedStatement.setLong(1, id);
                affectedRows = preparedStatement.executeUpdate();
                return affectedRows == 0 ? Optional.empty() : movie;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> update(Movie entity) {
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement("update movies set title = ?, director = ?, year = ? where id = ?");
        ) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDirector());
            preparedStatement.setInt(3, entity.getYear());
            preparedStatement.setLong(4, entity.getId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 0 ? Optional.ofNullable(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}