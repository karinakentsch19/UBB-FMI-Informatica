package com.example.comenzirestaurant.Domain;

import java.util.Objects;

public class MenuItem extends Entity<Integer>{
    private String category;

    private String item;

    private Float price;

    private String currency;

    public MenuItem(String category, String item, Float price, String currency) {
        this.category = category;
        this.item = item;
        this.price = price;
        this.currency = currency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(category, menuItem.category) && Objects.equals(item, menuItem.item) && Objects.equals(price, menuItem.price) && Objects.equals(currency, menuItem.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, item, price, currency);
    }
}
