package com.example.comenzirestaurant.Controller;

import com.example.comenzirestaurant.Domain.DTO.PlacedOrder;
import com.example.comenzirestaurant.Domain.MenuItem;
import com.example.comenzirestaurant.Service.OrderService;
import com.example.comenzirestaurant.Utils.Observer;
import com.example.comenzirestaurant.Utils.OrderStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StaffPageController implements Observer {
    @FXML
    private TableView<PlacedOrder> placedOrdersStaffTableView;

    @FXML
    private TableView<PlacedOrder> preparingOrdersTableView;

    @FXML
    private ObservableList<PlacedOrder> preparingOrdersModel = FXCollections.observableArrayList();


    @FXML
    private TableColumn<PlacedOrder, Integer> tableNumberPreparingOrdersColumn;

    @FXML
    private TableColumn<PlacedOrder, LocalDateTime> datePreparingOrdersColumn;

    @FXML
    private TableColumn<PlacedOrder, String> itemsPreparingOrdersColumn;

    @FXML
    private ObservableList<PlacedOrder> placedOrdersStaffModel = FXCollections.observableArrayList();

    @FXML
    private TableColumn<PlacedOrder, Integer> tableIdColumn;

    @FXML
    private TableColumn<PlacedOrder, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<PlacedOrder, String> itemsColumn;

    private OrderService orderService;

    public void setService(OrderService orderService){
        this.orderService = orderService;
        initializePlacedOrdersModel();
        initializePlacedOrdersTabel();
        initializePreparingOrdersModel();
        initializePreparingOrdersTabel();
        orderService.addObserver(this);
    }

    private void initializePlacedOrdersTabel(){
        tableIdColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//        itemsColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemNames"));
        itemsColumn.setCellValueFactory(data -> {
            PlacedOrder placedOrder = data.getValue();
            String items = "";
            Integer size = placedOrder.getMenuItemNames().size();
            for(int i = 0; i < size - 1; i++){
                items += placedOrder.getMenuItemNames().get(i) + ", ";
            }
            items += placedOrder.getMenuItemNames().get(size - 1);
            return javafx.beans.binding.Bindings.concat(items);
        });
        placedOrdersStaffTableView.setItems(placedOrdersStaffModel);
    }

    private void initializePreparingOrdersModel(){
        preparingOrdersModel.setAll(StreamSupport.stream(orderService.getAllPlacedOrders(OrderStatus.PREPARING).spliterator(), false).collect(Collectors.toList()));
    }

    private void initializePreparingOrdersTabel(){
        tableNumberPreparingOrdersColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        datePreparingOrdersColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//        itemsColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemNames"));
        itemsPreparingOrdersColumn.setCellValueFactory(data -> {
            PlacedOrder placedOrder = data.getValue();
            String items = "";
            Integer size = placedOrder.getMenuItemNames().size();
            for(int i = 0; i < size - 1; i++){
                items += placedOrder.getMenuItemNames().get(i) + ", ";
            }
            items += placedOrder.getMenuItemNames().get(size - 1);
            return javafx.beans.binding.Bindings.concat(items);
        });
        preparingOrdersTableView.setItems(preparingOrdersModel);
    }

    private void initializePlacedOrdersModel(){
        placedOrdersStaffModel.setAll(StreamSupport.stream(orderService.getAllPlacedOrders(OrderStatus.PLACED).spliterator(), false).collect(Collectors.toList()));
    }

    @Override
    public void update(OrderStatus status, Integer tableId) {
        initializePlacedOrdersModel();
        initializePreparingOrdersModel();
    }

    public void onPressPreparingThisOrderButton(ActionEvent actionEvent) {
        try{
             PlacedOrder placedOrder = placedOrdersStaffTableView.getSelectionModel().getSelectedItem();
             Integer orderId = placedOrder.getOrderId();
             Integer tableId = placedOrder.getTableId();
             LocalDateTime date = placedOrder.getDate();
             orderService.updateOrder(orderId, tableId, new ArrayList<>(), date, OrderStatus.PREPARING);
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    public void onPressDeliveredThisOrderButton(ActionEvent actionEvent) {
        try{
            PlacedOrder placedOrder = preparingOrdersTableView.getSelectionModel().getSelectedItem();
            Integer orderId = placedOrder.getOrderId();
            Integer tableId = placedOrder.getTableId();
            LocalDateTime date = placedOrder.getDate();
            orderService.updateOrder(orderId, tableId, new ArrayList<>(), date, OrderStatus.SERVED);
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }
}
