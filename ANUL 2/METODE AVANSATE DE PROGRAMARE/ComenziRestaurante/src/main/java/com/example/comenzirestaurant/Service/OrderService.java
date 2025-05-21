package com.example.comenzirestaurant.Service;

import com.example.comenzirestaurant.Domain.DTO.PlacedOrder;
import com.example.comenzirestaurant.Domain.Entity;
import com.example.comenzirestaurant.Domain.Order;
import com.example.comenzirestaurant.Repository.OrderRepository;
import com.example.comenzirestaurant.Utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService extends AbstractService{
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Integer tableId, List<Integer> menuItems){
        Order order = new Order(tableId, menuItems, LocalDateTime.now(), OrderStatus.PLACED);
        orderRepository.add(order);
        notifyAllObservers(OrderStatus.PLACED, null);
    }
    public Iterable<PlacedOrder> getAllPlacedOrders(OrderStatus status){
        return orderRepository.getAllOrdersByStatus(status);
    }

    public void updateOrder(Integer orderId, Integer tableId, List<Integer> menuItems,LocalDateTime date, OrderStatus status){
        Order order = new Order(tableId, menuItems, date, status);
        order.setId(orderId);
        orderRepository.update(order);
        notifyAllObservers(status, tableId);
    }
}
