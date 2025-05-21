package com.example.ofertedevacanta.Controller;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Domain.SpecialOffer;
import com.example.ofertedevacanta.Service.SpecialOfferService;
import com.example.ofertedevacanta.Utils.Hobby;
import com.example.ofertedevacanta.Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HotelSpecialOffersPageController implements Observer {

    @FXML
    private TableView<SpecialOffer> hotelSpecialOfferTableView;

    @FXML
    private ObservableList<SpecialOffer> model = FXCollections.observableArrayList();

    @FXML
    private TableColumn<SpecialOffer, LocalDate> startDateColumn;

    @FXML
    private TableColumn<SpecialOffer, LocalDate> endDateColumn;

    @FXML
    private TableColumn<SpecialOffer, Integer> percentsColumn;

    @FXML
    private DatePicker startDateDatePicker;

    @FXML
    private DatePicker endDateDatePicker;

    private SpecialOfferService specialOfferService;

    private Double hotelId;

    private LocalDate startDate, endDate;

    public void setService(SpecialOfferService specialOfferService, Double hotelId){
        this.specialOfferService = specialOfferService;
        this.hotelId = hotelId;
//        initializeModel();
        initializeTable();
        specialOfferService.addObserver(this);
    }

    private void initializeTable(){
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        percentsColumn.setCellValueFactory(new PropertyValueFactory<>("percents"));
        hotelSpecialOfferTableView.setItems(model);
    }

    private void initializeModel(){
        model.setAll(StreamSupport.stream(specialOfferService.getAllSpecialOffersFromADateAndAHotel(startDate, endDate, hotelId).spliterator(), false).collect(Collectors.toList()));
    }

    public void onPressSeeOffersButton(ActionEvent actionEvent) {
        startDate = startDateDatePicker.getValue();
        endDate = endDateDatePicker.getValue();
        initializeModel();

    }

    @Override
    public void update(Hobby hobby, String name, List<Client> clients) {
        initializeModel();
    }
}
