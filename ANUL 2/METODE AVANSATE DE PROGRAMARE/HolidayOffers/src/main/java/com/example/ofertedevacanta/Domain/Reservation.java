package com.example.ofertedevacanta.Domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation extends Entity<Double>{
    private Long clientId;

    private Double hotelId;

    private LocalDateTime startDate;

    private Integer noNights;

    public Reservation(Long clientId, Double hotelId, LocalDateTime startDate, Integer noNights) {
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.noNights = noNights;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getNoNights() {
        return noNights;
    }

    public void setNoNights(Integer noNights) {
        this.noNights = noNights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(hotelId, that.hotelId) && Objects.equals(startDate, that.startDate) && Objects.equals(noNights, that.noNights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, hotelId, startDate, noNights);
    }
}
