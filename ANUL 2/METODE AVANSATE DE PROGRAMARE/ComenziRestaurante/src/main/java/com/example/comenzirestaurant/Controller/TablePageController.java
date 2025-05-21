package com.example.comenzirestaurant.Controller;

import com.example.comenzirestaurant.Domain.DTO.PlacedOrder;
import com.example.comenzirestaurant.Domain.MenuItem;
import com.example.comenzirestaurant.Domain.Order;
import com.example.comenzirestaurant.Domain.Table;
import com.example.comenzirestaurant.Service.MenuItemService;
import com.example.comenzirestaurant.Service.OrderService;
import com.example.comenzirestaurant.Service.TableService;
import com.example.comenzirestaurant.Utils.Observer;
import com.example.comenzirestaurant.Utils.OrderStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TablePageController implements Observer {
    @FXML
    private VBox verticalBox;

    private TableService tableService;

    private MenuItemService menuItemService;

    private OrderService orderService;

    private Integer tableId;
    List<TableView<MenuItem>> allTables = new ArrayList<>();

    public void setService(TableService tableService, MenuItemService menuItemService, OrderService orderService, Integer tableId){
        this.tableService = tableService;
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.tableId = tableId;
        initializeVBox();
        orderService.addObserver(this);
    }

    private void initializeVBox(){
        for(String category : menuItemService.getAllCategories()){
            Label categoryNameLabel = new Label();
            TableView<MenuItem> table = new TableView<>();
            TableColumn<MenuItem,String> nameColumn = new TableColumn<>();
            TableColumn<MenuItem, String> priceColumn = new TableColumn<>();
            ObservableList<MenuItem> model = FXCollections.observableArrayList();

            model.setAll(StreamSupport.stream(menuItemService.getAllMenuItemsByCategory(category).spliterator(), false).collect(Collectors.toList()));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
            priceColumn.setCellValueFactory(data -> {
                MenuItem menuItem = data.getValue();
                String price = menuItem.getPrice() + " " + menuItem.getCurrency();
                return javafx.beans.binding.Bindings.concat(price);
            });
            table.getColumns().add(nameColumn);
            table.getColumns().add(priceColumn);
            table.setItems(model);
            categoryNameLabel.setText(category);
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            allTables.add(table);
            VBox vBox = new VBox(categoryNameLabel, table);
            verticalBox.getChildren().add(vBox);
        }
    }

    public void onPressPlaceOrderButton(ActionEvent actionEvent) {
        try{
            List<Integer> menuItemList = new ArrayList<>();
             for(TableView<MenuItem> table : allTables){
                 ObservableList<MenuItem> menuItems = table.getSelectionModel().getSelectedItems();
                 for(MenuItem item: menuItems){
                     menuItemList.add(item.getId());
                 }
             }
             orderService.addOrder(tableId,menuItemList);
        }catch (RuntimeException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("An error occured: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void update(OrderStatus status, Integer tableId) {
        initializeVBox();
        if (status == OrderStatus.PREPARING && Objects.equals(this.tableId, tableId)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TABLE " + tableId + " NOTIFICATION");
            alert.setContentText("Your order is being prepared!");
            alert.showAndWait();
        }
        else
            if(status == OrderStatus.SERVED && Objects.equals(this.tableId, tableId)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TABLE " + tableId + " NOTIFICATION");
                alert.setContentText("Your order has been delivered to your table!");
                alert.showAndWait();
            }
    }
}
