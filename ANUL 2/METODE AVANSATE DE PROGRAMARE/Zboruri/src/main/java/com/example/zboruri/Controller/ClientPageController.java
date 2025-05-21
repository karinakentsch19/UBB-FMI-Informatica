package com.example.zboruri.Controller;

import com.example.zboruri.Domain.Client;
import com.example.zboruri.Domain.Flight;
import com.example.zboruri.Repository.Paging.Page;
import com.example.zboruri.Repository.Paging.Pageable;
import com.example.zboruri.Service.ClientService;
import com.example.zboruri.Service.FlightService;
import com.example.zboruri.Service.TicketService;
import com.example.zboruri.Utils.Observer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientPageController implements Observer {
    @FXML
    private TableView<Flight> flightsTableView;

    @FXML
    private TableColumn<Flight, Long> flightIdColumn;
    @FXML
    private TableColumn<Flight, String> fromColumn;
    @FXML
    private TableColumn<Flight, String> toColumn;
    @FXML
    private TableColumn<Flight, LocalDateTime> departureTimeColumn;
    @FXML
    private TableColumn<Flight, LocalDateTime> landingTimeColumn;
    @FXML
    private TableColumn<Flight, Integer> seatsColumn;

    @FXML
    private TableColumn<Flight, Integer> availableSeatsColumn;

    @FXML
    private ObservableList<Flight> flightsModel = FXCollections.observableArrayList();

    @FXML
    private ObservableList<String> fromComboBoxModel = FXCollections.observableArrayList();

    @FXML
    private ObservableList<String> toComboBoxModel = FXCollections.observableArrayList();


    @FXML
    private ComboBox<String> fromComboBox;

    @FXML
    private ComboBox<String> toComboBox;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private Label pageNumberLabel;

    private int pageSize = 5;

    private int currentPage = 0;

    private int totalNumberOfElements = 0;

    private ClientService clientService;

    private FlightService flightService;

    private TicketService ticketService;

    private Client client;


    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;
    

    public void setService(ClientService clientService, FlightService flightService, TicketService ticketService, Client client){
        this.clientService = clientService;
        this.flightService = flightService;
        this.ticketService = ticketService;
        this.client = client;
//        initializeFlightsModel();
        initializeFlightsTable();

        initializeFromComboBoxModel();
        initializeFromComboBox();

        initializeToComboBoxModel();
        initializeToComboBox();

        this.ticketService.addObserver(this);
    }

    private void initializeFlightsTable(){
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<Flight, Long>("id"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("to"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureTime"));
        landingTimeColumn.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingTime"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));
        availableSeatsColumn.setCellValueFactory(cell -> {
            Flight flight = cell.getValue();
            return new SimpleIntegerProperty(ticketService.numberOfFreeTicketsForAFlight(flight.getId())).asObject();
        });
        flightsTableView.setItems(flightsModel);
    }

    private void initializeFlightsModel(){
        //Page<Flight> page = flightService.findAllOnPage(new Pageable(currentPage, pageSize));
        String from = fromComboBox.getValue();
        String to = toComboBox.getValue();
        LocalDate date = departureDatePicker.getValue();
        Page<Flight> page = flightService.filteredFlightsOnPage(from, to, date, new Pageable(currentPage, pageSize));
        int maxPage = (int) Math.ceil((double) page.getTotalNumberOfElements() / pageSize) - 1;
        if (maxPage < 0)
            maxPage = 0;
        if (currentPage > maxPage){
            currentPage = maxPage;
            page = flightService.findAllOnPage(new Pageable(currentPage, pageSize));
        }

        flightsModel.setAll(StreamSupport.stream(page.getElementsOnPage().spliterator(), false).collect(Collectors.toList()));

        totalNumberOfElements = page.getTotalNumberOfElements();

        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage + 1) * pageSize >= totalNumberOfElements);

        pageNumberLabel.setText((currentPage+1) + " / " + (maxPage+1));
    }

    private void initializeFromComboBox(){
        fromComboBox.setItems(fromComboBoxModel);
    }

    private void initializeToComboBox(){
        toComboBox.setItems(toComboBoxModel);
    }

    private void initializeFromComboBoxModel(){
        fromComboBoxModel.setAll(StreamSupport.stream(flightService.fromLocations().spliterator(), false).collect(Collectors.toList()));
    }

    private void initializeToComboBoxModel(){
        toComboBoxModel.setAll(StreamSupport.stream(flightService.toLocations().spliterator(), false).collect(Collectors.toList()));
    }

    public void onPressShowFlightsButton(ActionEvent actionEvent) {
//        String from = fromComboBox.getValue();
//        String to = toComboBox.getValue();
//        LocalDate date = departureDatePicker.getValue();
//        Iterable<Flight> filteredFligths = flightService.filteredFlights(from, to, date);
//        flightsModel.setAll(StreamSupport.stream(filteredFligths.spliterator(), false).collect(Collectors.toList()));
        initializeFlightsModel();
    }



    public void onPressBuyTicketButton(ActionEvent actionEvent) {
        Flight flight = flightsTableView.getSelectionModel().getSelectedItem();
        try{
            ticketService.buyTicket(client.getUsername(), flight.getId());
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }

    }

    @Override
    public void update(Long flightId) {
        Predicate<Flight> hasFlight = flight -> Objects.equals(flight.getId(), flightId);
        if (!flightsModel.stream().filter(hasFlight).toList().isEmpty())
            initializeFlightsModel();
    }

    public void onPressPreviousButton(ActionEvent actionEvent) {
        currentPage--;
        initializeFlightsModel();
    }

    public void onPressNextButton(ActionEvent actionEvent) {
        currentPage++;
        initializeFlightsModel();
    }
}
