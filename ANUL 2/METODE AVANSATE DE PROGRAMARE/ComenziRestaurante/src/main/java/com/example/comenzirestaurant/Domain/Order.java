package com.example.comenzirestaurant.Domain;

import com.example.comenzirestaurant.Utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order extends Entity<Integer>{
    private Integer tableId;

    private List<Integer> menuItems;

    private LocalDateTime date;

    private OrderStatus status;

    public Order(Integer tableId, List<Integer> menuItems, LocalDateTime date, OrderStatus status) {
        this.tableId = tableId;
        this.menuItems = menuItems;
        this.date = date;
        this.status = status;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public List<Integer> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Integer> menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(tableId, order.tableId) && Objects.equals(menuItems, order.menuItems) && Objects.equals(date, order.date) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, menuItems, date, status);
    }
}
