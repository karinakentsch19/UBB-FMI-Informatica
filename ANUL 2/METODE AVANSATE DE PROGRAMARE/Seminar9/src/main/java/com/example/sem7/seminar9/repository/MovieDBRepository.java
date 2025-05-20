package com.example.sem7.seminar9.repository;

import com.example.sem7.seminar9.DTO.MovieFilterDTO;
import com.example.sem7.seminar9.domain.Movie;
import com.example.sem7.seminar9.repository.paging.Page;
import com.example.sem7.seminar9.repository.paging.Pageable;
import com.example.sem7.seminar9.repository.paging.PagingRepository;
import com.example.sem7.seminar9.util.Pair;

import java.sql.*;
import java.util.*;

public class MovieDBRepository implements MovieRepository{
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
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movies WHERE id = ?")
        ){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                Integer year = resultSet.getInt("year");

                Movie movie = new Movie(title, director, year);
                movie.setId(id);

                return Optional.ofNullable(movie);
            }
            return Optional.empty();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movies")
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                Integer year = resultSet.getInt("year");

                Movie movie = new Movie(title, director, year);
                movie.setId(id);

                movies.add(movie);
            }
            return movies;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> save(Movie entity) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO movies " +
                    "(title, director, year) VALUES (?,?,?)");
        ) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDirector());
            preparedStatement.setInt(3, entity.getYear());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0){
                return Optional.empty();
            }
            else return Optional.ofNullable(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> delete(Long id) {
        Optional<Movie> opt = this.findOne(id);
        int affectedRows = 0;

        if (opt.isPresent()) {
            try(Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM movies WHERE id = ?");
            ) {
                preparedStatement.setLong(1, id);

                affectedRows = preparedStatement.executeUpdate();

                return affectedRows == 0 ? Optional.empty() : opt;
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> update(Movie entity) {
        try(Connection connection= DriverManager.getConnection(this.url,this.username,this.password);
            PreparedStatement preparedStatement=connection.prepareStatement("UPDATE movies SET title = ?, director = ?, year = ? WHERE id = ?");

        ){
            preparedStatement.setString(1,entity.getTitle());
            preparedStatement.setString(2,entity.getDirector());
            preparedStatement.setInt(3,entity.getYear());
            preparedStatement.setLong(4,entity.getId());
            int affectedRows=preparedStatement.executeUpdate();
            return affectedRows == 0 ? Optional.of(entity) : Optional.empty();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Movie> findAllOnPage(Pageable pageable) {
        return findAllFilter(pageable, null);
    }

    @Override
    public List<Integer> getYears() {
        List<Integer> yearList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT distinct year FROM movies order by year");
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Integer year = resultSet.getInt("year");
                yearList.add(year);
            }
            return yearList;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Pair<String, List<Object>> toSql(MovieFilterDTO movieFilterDTO){
        if (movieFilterDTO == null)
            return new Pair<>("", Collections.emptyList());

        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        movieFilterDTO.getYearFilter().ifPresent(f -> {
            conditions.add("year = ?");
            parameters.add(f);
        });

        movieFilterDTO.getTitleFilter().ifPresent(f -> {
            conditions.add("title like ?");
            parameters.add("%" + f + "%");
        });

        movieFilterDTO.getDirectorFilter().ifPresent(f ->{
            conditions.add("director like ?");
            parameters.add("%" + f +"%");
        });

        String sql =  String.join(" and ", conditions);
        return  new Pair<>(sql, parameters);
    }

    @Override
    public Page<Movie> findAllFilter(Pageable pageable, MovieFilterDTO filter) {
        List<Movie> movies = new ArrayList<>();
        String selectSQL = "SELECT * FROM movies";
        String countSQL = "SELECT COUNT(*) AS count FROM movies";
        Pair<String, List<Object>> filterSQL = toSql(filter);
        if(!filterSQL.getFirst().isEmpty()){
            countSQL = countSQL + " WHERE " + filterSQL.getFirst();
            selectSQL = selectSQL + " WHERE " + filterSQL.getFirst();
        }

        selectSQL+= " LIMIT ? OFFSET ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pageStatement = connection.prepareStatement(selectSQL);
             PreparedStatement countStatement = connection.prepareStatement(countSQL)
        ){
            int c = 1;
            if(filterSQL != null)
                for(Object param : filterSQL.getSecond()){
                    pageStatement.setObject(c, param);
                    countStatement.setObject(c, param);
                    c++;
                }
            pageStatement.setInt(c++, pageable.getPageSize());
            pageStatement.setInt(c++, pageable.getPageSize() * pageable.getPageNr());

            try (
                    ResultSet pageResultSet = pageStatement.executeQuery();
                    ResultSet countResultSet = countStatement.executeQuery();
            ) {
                int count = 0;
                if (countResultSet.next()) {
                    count = countResultSet.getInt("count");
                }

                while(pageResultSet.next()){
                    Long id = pageResultSet.getLong("id");
                    String title = pageResultSet.getString("title");
                    String director = pageResultSet.getString("director");
                    Integer year = pageResultSet.getInt("year");

                    Movie movie = new Movie(title, director, year);
                    movie.setId(id);

                    movies.add(movie);
                }
                return new Page<>(movies, count);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

