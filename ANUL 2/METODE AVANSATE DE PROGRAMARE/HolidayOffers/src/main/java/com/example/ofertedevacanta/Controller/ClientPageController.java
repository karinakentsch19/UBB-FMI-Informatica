package com.example.ofertedevacanta.Controller;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Domain.DTO.ExtendedSpecialOffer;
import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Domain.Location;
import com.example.ofertedevacanta.HelloApplication;
import com.example.ofertedevacanta.Service.*;
import com.example.ofertedevacanta.Utils.Hobby;
import com.example.ofertedevacanta.Utils.Observer;
import com.example.ofertedevacanta.Utils.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientPageController implements Observer {

    private ClientService clientService;
    private HotelService hotelService;

    private ReservationService reservationService;

    private SpecialOfferService specialOfferService;

    private LocationService locationService;

    @FXML
    private TableView<Hotel> hotelsTableView;

    @FXML
    private ObservableList<Hotel> hotelsModel = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Hotel, String> hotelNameColumn;

    @FXML
    private TableColumn<Hotel, Integer> noRoomsColumn;

    @FXML
    private TableColumn<Hotel, Double> pricePerNightColumn;

    @FXML
    private TableColumn<Hotel, Type> typeColumn;

    @FXML
    private ComboBox<String> hotelLocationComboBox;

    @FXML
    private TableView<ExtendedSpecialOffer> specialOffersTableView;

    @FXML
    private ObservableList<ExtendedSpecialOffer> specialOffersModel = FXCollections.observableArrayList();

    @FXML
    private TableColumn<ExtendedSpecialOffer, String> specialOfferHotelNameColumn;
    @FXML
    private TableColumn<ExtendedSpecialOffer, LocalDate> startDateColumn;
    @FXML
    private TableColumn<ExtendedSpecialOffer, LocalDate> endDateColumn;
    @FXML
    private TableColumn<ExtendedSpecialOffer, Integer> percentsColumn;

    @FXML
    private TextField hotelNameTextField;

    @FXML
    private TextField noNightsTextField;

    @FXML
    private DatePicker startDateReservationDatePicker;

    private Client client;
    @FXML
    private ObservableList<String> comboBoxModel = FXCollections.observableArrayList();

    public void setService(ClientService clientService, HotelService hotelService, LocationService locationService, ReservationService reservationService, SpecialOfferService specialOfferService, Client client){
        this.client = client;
        this.clientService = clientService;
        this.hotelService = hotelService;
        this.locationService = locationService;
        this.reservationService = reservationService;
        this.specialOfferService = specialOfferService;
        setComboBoxModel();
        setValuesComboBox();
        initializeHotelModel();
        initializeHotelTable();
        initializeSpecialOffersModel();
        initializeSpecialOffersTable();
        reservationService.addObserver(this);
    }

    public void initializeHotelTable(){
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        noRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("noRooms"));
        pricePerNightColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        hotelsTableView.setItems(hotelsModel);
    }

    private void setComboBoxModel(){
        comboBoxModel.setAll(StreamSupport.stream(locationService.getAllLocations().spliterator(), false).collect(Collectors.toList()));
    }

    private void setValuesComboBox(){
        hotelLocationComboBox.setItems(comboBoxModel);
    }

    private void initializeHotelModel(){
        String locationName = hotelLocationComboBox.getValue();
        hotelsModel.setAll(StreamSupport.stream(hotelService.getAllHotelsFromALocation(locationName).spliterator(), false).collect(Collectors.toList()));
    }
    private void initializeSpecialOffersTable(){
        specialOfferHotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        percentsColumn.setCellValueFactory(new PropertyValueFactory<>("percents"));
        specialOffersTableView.setItems(specialOffersModel);
    }

    private void initializeSpecialOffersModel(){
        specialOffersModel.setAll(StreamSupport.stream(specialOfferService.getAllSpecialOffersForAClient(client.getFidelityGrade()).spliterator(), false).collect(Collectors.toList()));
    }



    public void onPressBookHolidayButton(ActionEvent actionEvent) {
        try{
            String hotelName = hotelNameTextField.getText();
            Integer noNights = Integer.valueOf(noNightsTextField.getText());
            LocalDateTime startDate = startDateReservationDatePicker.getValue().atTime(LocalTime.now());
            reservationService.addReservation(client.getId(), hotelName, startDate, noNights);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("RESERVATION SUCCESSFUL");
            alert.setContentText(client.getName() + " ,you have booked a holiday at: " + hotelName + " for: " + noNights + " nights, starting with: " + startDate.toLocalDate());
            alert.showAndWait();
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
        finally {
            hotelNameTextField.clear();
            noNightsTextField.clear();
        }
    }

    public void showHotelsSpecialOffers(MouseEvent mouseEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/hotelSpecialOffersPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 500);
            Stage newStage = new Stage();

            Hotel hotel = hotelsTableView.getSelectionModel().getSelectedItem();
            newStage.setTitle(hotel.getHotelName());
            newStage.setScene(scene);

            HotelSpecialOffersPageController hotelSpecialOffersPageController = fxmlLoader.getController();
            hotelSpecialOffersPageController.setService(specialOfferService, hotel.getId());
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Hobby hobby, String hotelName, List<Client> clients) {
        initializeHotelModel();
        initializeSpecialOffersModel();
        if (client.getHobby() == hobby && clients.contains(client)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("NOTIFICATION");
            alert.setContentText(client.getName() + " ,another client has made a reservation at " + hotelName);
            alert.showAndWait();
        }
    }

    public void getSelectedLocation(ActionEvent actionEvent) {
        initializeHotelModel();
    }
}
