package com.example.ofertedevacanta.Domain.DTO;

import java.time.LocalDate;
import java.util.Objects;

public class ExtendedSpecialOffer {
    private String hotelName;
    private LocalDate startDate, endDate;

    private Integer percents;

    public ExtendedSpecialOffer(String hotelName, LocalDate startDate, LocalDate endDate, Integer percents) {
        this.hotelName = hotelName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percents = percents;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedSpecialOffer that = (ExtendedSpecialOffer) o;
        return Objects.equals(hotelName, that.hotelName) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(percents, that.percents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelName, startDate, endDate, percents);
    }
}
