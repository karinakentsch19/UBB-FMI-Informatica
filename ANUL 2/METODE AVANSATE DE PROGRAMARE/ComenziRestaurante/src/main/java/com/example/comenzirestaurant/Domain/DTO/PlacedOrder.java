package com.example.comenzirestaurant.Domain.DTO;

import com.example.comenzirestaurant.Domain.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PlacedOrder{

    private Integer orderId;
    private Integer tableId;

    private LocalDateTime date;

    private List<String> menuItemNames;

    public PlacedOrder(Integer orderId, Integer tableId, LocalDateTime date, List<String> menuItemNames) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.date = date;
        this.menuItemNames = menuItemNames;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<String> getMenuItemNames() {
        return menuItemNames;
    }

    public void setMenuItemNames(List<String> menuItemNames) {
        this.menuItemNames = menuItemNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacedOrder that = (PlacedOrder) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(tableId, that.tableId) && Objects.equals(date, that.date) && Objects.equals(menuItemNames, that.menuItemNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, tableId, date, menuItemNames);
    }
}
