package com.example.faptebune.Controller;

import com.example.faptebune.Domain.Nevoie;
import com.example.faptebune.Domain.Persoana;
import com.example.faptebune.Service.NevoieService;
import com.example.faptebune.Service.PersoanaService;
import com.example.faptebune.Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MyPageController implements Observer {
    @FXML
    private ObservableList<Nevoie> nevoiOrasComunTableModel = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Nevoie> fapteBuneTableModel = FXCollections.observableArrayList();

    @FXML
    private TableView<Nevoie> nevoiDinOrasulMeuTable;

    @FXML
    private TableView<Nevoie> fapteBuneTable;


    @FXML
    private TableColumn<Nevoie, String> titluNevoiOrasColumn;


    @FXML
    private TableColumn<Nevoie, String> descriereNevoiOrasColumn;

    @FXML
    private TableColumn<Nevoie, LocalDateTime> deadlineNevoiOrasColumn;

    @FXML
    private TableColumn<Nevoie, Long> omInNevoieNevoiOrasColumn;

    @FXML
    private TableColumn<Nevoie, String> statusNevoiOrasColumn;

    @FXML
    private TableColumn<Nevoie, String> titluFapteBuneColumn;


    @FXML
    private TableColumn<Nevoie, String> descriereFapteBuneColumn;

    @FXML
    private TableColumn<Nevoie, LocalDateTime> deadlineFapteBuneColumn;

    @FXML
    private TableColumn<Nevoie, Long> omInNevoieFapteBuneColumn;

    @FXML
    private TableColumn<Nevoie, String> statusFapteBuneColumn;

    @FXML
    private TextField titluAdaugaONevoieTextfield;

    @FXML
    private TextField descriereAdaugaONevoieTextfield;

    @FXML
    private DatePicker deadlineDatepicker;

    private PersoanaService persoanaService;

    private NevoieService nevoieService;

    private Persoana myPersoana;

    public void setService(PersoanaService persoanaService, NevoieService nevoieService, Persoana persoana) {
        this.persoanaService = persoanaService;
        this.nevoieService = nevoieService;
        this.myPersoana = persoana;
        intializeNevoiOrasComunTableModel();
        initializeNevoiDinOrasulMeuTable();

        initializeFapteBuneTableModel();
        initializeFapteBuneTable();

        nevoieService.addObserver(this);

    }

    private void initializeNevoiDinOrasulMeuTable() {
        titluNevoiOrasColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereNevoiOrasColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineNevoiOrasColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        omInNevoieNevoiOrasColumn.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        statusNevoiOrasColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        nevoiDinOrasulMeuTable.setItems(nevoiOrasComunTableModel);
    }

    private void initializeFapteBuneTable() {
        titluFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        omInNevoieFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        statusFapteBuneColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        fapteBuneTable.setItems(fapteBuneTableModel);
    }

    private void intializeNevoiOrasComunTableModel() {
        nevoiOrasComunTableModel.setAll(StreamSupport.stream(nevoieService.getNevoiCuOmSalvatorNullDinOrasDat(myPersoana.getOras(), myPersoana.getId()).spliterator(), false).collect(Collectors.toList()));
    }

    private void initializeFapteBuneTableModel() {
        fapteBuneTableModel.setAll(StreamSupport.stream(nevoieService.getNevoiPentruOmSalvatorDat(myPersoana.getId()).spliterator(), false).collect(Collectors.toList()));
    }


    public void onPressAddNevoieButton(ActionEvent actionEvent) {
        try {
            String titlu = titluAdaugaONevoieTextfield.getText();
            String descriere = descriereAdaugaONevoieTextfield.getText();
            LocalDateTime deadline = deadlineDatepicker.getValue().atTime(LocalTime.now());
            Long omInNevoie = myPersoana.getId();

            nevoieService.addNevoie(titlu, descriere, deadline, omInNevoie);
        } catch (
                RuntimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();

        } finally {
            descriereAdaugaONevoieTextfield.clear();
            titluAdaugaONevoieTextfield.clear();
        }
    }

    @Override
    public void update() {
        initializeFapteBuneTableModel();
        intializeNevoiOrasComunTableModel();
    }

    public void rezolvaONevoie(MouseEvent mouseEvent) {

        try {
            Nevoie nevoie = nevoiDinOrasulMeuTable.getSelectionModel().getSelectedItem();
            nevoieService.updateNevoie(nevoie.getId(), nevoie.getTitlu(), nevoie.getDescriere(), nevoie.getDeadline(), nevoie.getOmInNevoie(), myPersoana.getId(), "Erou gasit!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MESAJ DE CONFIRMARE");
            alert.setContentText("Ai ajutat un om!");
            alert.showAndWait();
        } catch (RuntimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();

        }

    }
}