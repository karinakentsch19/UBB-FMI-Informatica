package ro.ubbbcluj.cs.map;

import ro.ubbbcluj.cs.map.domain.Movie;
import ro.ubbbcluj.cs.map.repository.MovieDBRepository;
import ro.ubbbcluj.cs.map.repository.Repository;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Repository<Long, Movie> repository = new MovieDBRepository("jdbc:postgresql://localhost:5432/Movies", "postgres", "karina19");
        System.out.println(repository.findOne(1L).get());
        System.out.println(repository.findAll());

        Movie movie = new Movie("Lord of the rings", "Paul", 2003);
        repository.save(movie);
        System.out.println(repository.findAll());

        repository.delete(2L);
        System.out.println(repository.findAll());
    }
}