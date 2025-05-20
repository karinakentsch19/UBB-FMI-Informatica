package ro.ubbcluj.cs.map.seminar7.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.seminar7.domain.Movie;
import ro.ubbcluj.cs.map.seminar7.observer.MovieChangeEvent;
import ro.ubbcluj.cs.map.seminar7.observer.Observable;
import ro.ubbcluj.cs.map.seminar7.observer.Observer;
import ro.ubbcluj.cs.map.seminar7.repository.Repository;
import ro.ubbcluj.cs.map.seminar7.service.MovieService;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieController implements Observer<MovieChangeEvent> {
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, Long> columnId;
    @FXML
    private TableColumn<Movie, String> columnTitle;
    @FXML
    private TableColumn<Movie, String> columnDirector;
    @FXML
    private TableColumn<Movie, Long> columnYear;

    private ObservableList<Movie> model = FXCollections.observableArrayList();

    private MovieService movieService;



    @FXML
    public void initialize(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        movieTable.setItems(model);
    }

    private void initModel(){
        model.setAll(StreamSupport.stream(movieService.findAll().spliterator(),false).collect(Collectors.toList()));
    }

    private Repository<Long, Movie> movieRepository;

    public void setMovieRepository(MovieService movieService) {
        this.movieService = movieService;
        movieService.addObserver(this);
        initModel();
    }


    private void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    public void onDelete(ActionEvent actionEvent) {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if(selectedMovie == null){
            showMessage(null, Alert.AlertType.ERROR,"Error", "Please select a movie");
        }
        else {
            movieService.delete(selectedMovie.getId());
//            initModel();
        }
    }

    @Override
    public void update(MovieChangeEvent evemt) {
        initModel();
    }
}
