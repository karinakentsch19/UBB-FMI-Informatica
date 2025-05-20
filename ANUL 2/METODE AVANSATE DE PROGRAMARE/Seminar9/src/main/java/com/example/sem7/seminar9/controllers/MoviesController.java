package com.example.sem7.seminar9.controllers;

import com.example.sem7.seminar9.DTO.MovieFilterDTO;
import com.example.sem7.seminar9.domain.Movie;
import com.example.sem7.seminar9.observer.MovieChangeEvent;
import com.example.sem7.seminar9.observer.Observer;
import com.example.sem7.seminar9.repository.paging.Page;
import com.example.sem7.seminar9.repository.paging.Pageable;
import com.example.sem7.seminar9.service.MovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MoviesController implements Observer<MovieChangeEvent> {

    private MovieService movieService;

    @FXML
    TableView<Movie> movieTableView;

    @FXML
    TableColumn<Movie, Long> idMovie;

    @FXML
    TableColumn<Movie, String> titleMovie;

    @FXML
    TableColumn<Movie, String> directorMovie;

    @FXML
    TableColumn<Movie, Integer> yearMovie;

    @FXML
    TextField titleField;

    @FXML
    TextField directorField;

    @FXML
    TextField yearField;

    @FXML
    Button prevButton;

    @FXML
    Button nextButton;

    @FXML
    ComboBox<Integer> yearFilterComboBox;

    @FXML
    TextField yearFilterTitleTextField;

    @FXML
    TextField yearFilterDirectorTextField;

    @FXML
    Label pageLabel;

    private int pageSize = 5;
    private int currentPage = 0;
    private int totalNrOfElems = 0;

    private MovieFilterDTO filterDTO = new MovieFilterDTO();

    ObservableList<Movie> moviesModel = FXCollections.observableArrayList();

    public void setMovieService(MovieService movieService){
        this.movieService = movieService;
        movieService.addObserver(this);
        initModel();
        initYearsCombo();
    }

    private void initModel() {
        Page<Movie> page = movieService.findAllFilter(new Pageable(currentPage, pageSize), filterDTO);

        int maxPage = (int) Math.ceil((double) page.getTotalNrOfElems() / pageSize) - 1;
        if (maxPage == -1)
            maxPage = 0;
        if (currentPage > maxPage) {
            currentPage = maxPage;

            page = movieService.findAllFilter(new Pageable(currentPage, pageSize), filterDTO);
        }

        moviesModel.setAll(StreamSupport.stream(page.getElementsOnPage().spliterator(),
                false).collect(Collectors.toList()));

        totalNrOfElems = page.getTotalNrOfElems();

        prevButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage + 1) * pageSize >= totalNrOfElems);
        pageLabel.setText("Page " + (currentPage + 1) + " of " + (maxPage + 1));
    }

    @FXML
    public void initialize() {
        movieTableView.setItems(moviesModel);

        idMovie.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleMovie.setCellValueFactory(new PropertyValueFactory<>("title"));
        directorMovie.setCellValueFactory(new PropertyValueFactory<>("director"));
        yearMovie.setCellValueFactory(new PropertyValueFactory<>("year"));


        yearFilterComboBox.getSelectionModel().selectedItemProperty().addListener(observable -> {
            filterDTO.setYearFilter(Optional.ofNullable(yearFilterComboBox.getSelectionModel().getSelectedItem()));
            currentPage = 0;
            initModel();
        });

        yearFilterTitleTextField.textProperty().addListener(observable ->{
            filterDTO.setTitleFilter(Optional.ofNullable(yearFilterTitleTextField.getText()));
            currentPage = 0;
            initModel();
        });

        yearFilterDirectorTextField.textProperty().addListener(observable -> {
            filterDTO.setDirectorFilter(Optional.ofNullable(yearFilterDirectorTextField.getText()));
            currentPage = 0;
            initModel();
        });

    }

    public void onPressDelete(ActionEvent actionEvent) {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            movieService.delete(selectedMovie.getId());
//            initModel();
        }
        else {
            MessageAlert.showErrorMessage(null, "No movie selected");
        }
    }

    public void onPressAdd(ActionEvent actionEvent) {
        String title = titleField.getText();
        String director = directorField.getText();
        Integer year = 0;

        try {
            year = Integer.valueOf(yearField.getText());
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        } finally {
            titleField.clear();
            directorField.clear();
            yearField.clear();
        }
        if (year == 0)
            return;

        movieService.save(new Movie(title, director, year));
//        initModel();
    }

    @Override
    public void update(MovieChangeEvent movieChangeEvent) {
        initModel();
    }

    public void onPressPrev(ActionEvent actionEvent) {
        currentPage--;
        initModel();
    }

    public void onPressNext(ActionEvent actionEvent) {
        currentPage++;
        initModel();
    }

    public void initYearsCombo(){
        List<Integer> years = movieService.getDistinctYears();
        years.add(0, null);
        yearFilterComboBox.getItems().setAll(years);
    }
}
