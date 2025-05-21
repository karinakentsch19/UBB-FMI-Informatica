package com.example.ofertedevacanta.Domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.PrimitiveIterator;

public class SpecialOffer extends Entity<Double>{
    private Double hotelId;
    private LocalDate startDate, endDate;

    private Integer percents;

    public SpecialOffer(Double hotelId, LocalDate startDate, LocalDate endDate, Integer percents) {
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percents = percents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialOffer that = (SpecialOffer) o;
        return Objects.equals(hotelId, that.hotelId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(percents, that.percents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, startDate, endDate, percents);
    }

    public Double getHotelId() {
        return hotelId;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getPercents() {
        return percents;
    }

    public void setPercents(Integer percents) {
        this.percents = percents;
    }
}
