package com.example.comenzirestaurant;

import com.example.comenzirestaurant.Controller.StaffPageController;
import com.example.comenzirestaurant.Controller.TablePageController;
import com.example.comenzirestaurant.Domain.MenuItem;
import com.example.comenzirestaurant.Domain.Order;
import com.example.comenzirestaurant.Domain.Table;
import com.example.comenzirestaurant.Repository.MenuItemRepository;
import com.example.comenzirestaurant.Repository.OrderRepository;
import com.example.comenzirestaurant.Repository.TableRepository;
import com.example.comenzirestaurant.Service.MenuItemService;
import com.example.comenzirestaurant.Service.OrderService;
import com.example.comenzirestaurant.Service.TableService;
import com.example.comenzirestaurant.Validation.AbstractValidator;
import com.example.comenzirestaurant.Validation.MenuItemValidator;
import com.example.comenzirestaurant.Validation.OrderValidator;
import com.example.comenzirestaurant.Validation.TableValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/staffPage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Staff Page");
        stage.setScene(scene);

        AbstractValidator<Table> tableAbstractValidator = new TableValidator();
        TableRepository tableRepository = new TableRepository("jdbc:postgresql://localhost:5432/ComenziRestaurante", "postgres", "karina19", tableAbstractValidator);
        TableService tableService = new TableService(tableRepository);

        AbstractValidator<MenuItem> menuItemAbstractValidator = new MenuItemValidator();
        MenuItemRepository menuItemRepository = new MenuItemRepository("jdbc:postgresql://localhost:5432/ComenziRestaurante", "postgres", "karina19", menuItemAbstractValidator);
        MenuItemService menuItemService = new MenuItemService(menuItemRepository);

        AbstractValidator<Order> orderAbstractValidator = new OrderValidator();
        OrderRepository orderRepository = new OrderRepository("jdbc:postgresql://localhost:5432/ComenziRestaurante", "postgres", "karina19", orderAbstractValidator);
        OrderService orderService = new OrderService(orderRepository);

        StaffPageController staffPageController = fxmlLoader.getController();
        staffPageController.setService(orderService);
        stage.show();


        for (Table table : tableService.getAll()) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Views/tablePage-view.fxml"));
            Scene newScene = new Scene(loader.load(), 600, 800);
            Stage newStage = new Stage();
            newStage.setTitle("Table number " + table.getId().toString());
            newStage.setScene(newScene);

            TablePageController tablePageController = loader.getController();
            tablePageController.setService(tableService, menuItemService, orderService, table.getId());
            newStage.show();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}