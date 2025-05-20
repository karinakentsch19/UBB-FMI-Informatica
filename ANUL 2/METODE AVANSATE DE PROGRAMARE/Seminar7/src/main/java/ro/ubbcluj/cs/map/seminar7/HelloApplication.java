package ro.ubbcluj.cs.map.seminar7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.seminar7.controller.MovieController;
import ro.ubbcluj.cs.map.seminar7.domain.Movie;
import ro.ubbcluj.cs.map.seminar7.repository.MovieDBRepository;
import ro.ubbcluj.cs.map.seminar7.repository.Repository;
import ro.ubbcluj.cs.map.seminar7.service.MovieService;

import java.io.IOException;

public class HelloApplication extends Application {
//    Repository<Long, Movie> movieRepository =
//            new MovieDBRepository("jdbc:postgresql://localhost:5432/Movies", "postgres", "karina19");
//    MovieService movieService = new MovieService(movieRepository);
    @Override
    public void start(Stage stage) throws IOException {
        Repository<Long, Movie> movieRepository =
                new MovieDBRepository("jdbc:postgresql://localhost:5432/Movies", "postgres", "karina19");
        MovieService movieService = new MovieService(movieRepository);
        movieRepository.save(new Movie("T","D",2023));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/movies-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MovieController movieController = fxmlLoader.getController();
        movieController.setMovieRepository(movieService);
        stage.setTitle("Movies");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}