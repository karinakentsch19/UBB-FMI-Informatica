package com.example.trenuri.Controller;

import com.example.trenuri.Domain.City;
import com.example.trenuri.Domain.TrainStation;
import com.example.trenuri.HelloApplication;
import com.example.trenuri.Service.CityService;
import com.example.trenuri.Service.TrainStationService;
import com.example.trenuri.Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientPageController implements Observer {

    @FXML
    private ComboBox<String> departureCityComboBox;

    @FXML
    private ComboBox<String> destinationCityComboBox;

    @FXML
    private ObservableList<String> departureCityComboBoxModel = FXCollections.observableArrayList();

    @FXML
    private ObservableList<String> destinationCityComboBoxModel = FXCollections.observableArrayList();

    @FXML
    private CheckBox checkBox;

    @FXML
    private ListView<String> routesListView;

    @FXML
    private ObservableList<String> routesModel = FXCollections.observableArrayList();

    @FXML
    private Label notificationLabel;

    private CityService cityService;

    private TrainStationService trainStationService;

    private String currentDepartureCity = "", currentDestinationCity = "";

    private Integer numberOfStations = 0;

    private Float pricePerStation = 10.0F;

    public void setService(CityService cityService, TrainStationService trainStationService){
        this.cityService = cityService;
        this.trainStationService = trainStationService;
        trainStationService.addObserver(this);
        initDestinationCityComboBoxModel();
        initDestinationCityComboBox();
        initDepartureCityComboBoxModel();
        initDepartureCityComboBox();
    }

    private void initDepartureCityComboBox(){
        departureCityComboBox.setItems(departureCityComboBoxModel);
    }
    private void initDepartureCityComboBoxModel(){
        departureCityComboBoxModel.setAll(StreamSupport.stream(cityService.getAllCityNames().spliterator(), false).collect(Collectors.toList()));
    }
    private void initDestinationCityComboBox(){
        destinationCityComboBox.setItems(destinationCityComboBoxModel);
    }
    private void initDestinationCityComboBoxModel(){
        destinationCityComboBoxModel.setAll(StreamSupport.stream(cityService.getAllCityNames().spliterator(), false).collect(Collectors.toList()));
    }

    private void initListModelForDirectRoute(Integer departureCityId, Integer destinationCityId){
        TrainStation directRoute = trainStationService.findDirectRoute(departureCityId, destinationCityId);
        City departureCity = cityService.findCityById(directRoute.getDepartureCityId());
        City destinationCity = cityService.findCityById(directRoute.getDestinationCityId());
        String departureCityName = departureCity.getName();
        String destinationCityName = destinationCity.getName();
        numberOfStations = 1;
        String route = departureCityName + " ---" + directRoute.getId() + "---> " +destinationCityName + pricePerStation*numberOfStations;

        routesModel.setAll(route);

    }
    private void initListModelForNonDirectRoute(Integer departureCityId, Integer destinationCityId){
        List<String> routes = new ArrayList<>();

        for (List<TrainStation> trainStationList: trainStationService.getAllNonDirectRoutes(departureCityId, destinationCityId)){
            String entireRoute = "";
            numberOfStations = 0;
            TrainStation trainStation = trainStationList.getFirst();
            String departureCity1 = cityService.findCityById(trainStation.getDepartureCityId()).getName();
            entireRoute += departureCity1 + " ---" + trainStation.getId() + "--->";
            numberOfStations++;
            for (int i = 1; i < trainStationList.size(); i++){
                City departureCity = cityService.findCityById(trainStationList.get(i).getDepartureCityId());
                City destinationCity = cityService.findCityById(trainStationList.get(i).getDestinationCityId());
                String departureCityName = departureCity.getName();
                String destinationCityName = destinationCity.getName();
                String route = departureCityName + " ---" + trainStationList.get(i).getId() + "---> ";
                entireRoute += route;
                numberOfStations++;
            }
            TrainStation trainStation1 = trainStationList.getLast();
            String destinationCity1 = cityService.findCityById(trainStation1.getDestinationCityId()).getName();
            entireRoute += destinationCity1 + " " + pricePerStation * numberOfStations;
            routes.add(entireRoute);
        }
        routesModel.setAll(routes);
    }


    public void onPressSearchButton(ActionEvent actionEvent) {
        try {
           currentDepartureCity = departureCityComboBox.getValue();
           currentDestinationCity = destinationCityComboBox.getValue();
           if (checkBox.isSelected()){
               City departure = cityService.findCityByName(currentDepartureCity);
               City destination = cityService.findCityByName(currentDestinationCity);
               initListModelForDirectRoute(departure.getId(), destination.getId());
           }
           else{
               City departure = cityService.findCityByName(currentDepartureCity);
               City destination = cityService.findCityByName(currentDestinationCity);
               initListModelForNonDirectRoute(departure.getId(), destination.getId());
           }
            routesListView.setItems(routesModel);
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void update(Integer departureCityId, Integer destinationCityId) {
        City departure = cityService.findCityById(departureCityId);
        City destination = cityService.findCityById(destinationCityId);
        Integer contor = trainStationService.countHowManyWindowsHaveThisFilter(currentDepartureCity, currentDestinationCity) - 1;
        notificationLabel.setText(contor + " other user(s) are looking at the same route.");
    }

    @Override
    public boolean hasFilters(String departureCity, String destinationCity) {
        return Objects.equals(currentDestinationCity, destinationCity) && Objects.equals(currentDepartureCity, departureCity);
    }
}
