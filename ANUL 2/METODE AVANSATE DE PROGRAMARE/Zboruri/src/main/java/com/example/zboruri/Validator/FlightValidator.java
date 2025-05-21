package com.example.zboruri.Validator;

import com.example.zboruri.Domain.Flight;

public class FlightValidator implements AbstractValidator<Flight>{
    @Override
    public void validate(Flight flight) {
        String errors = "";
        if (flight.getFrom().isEmpty())
            errors += "Invalid From\n";
        if (flight.getTo().isEmpty())
            errors += "Invalid To\n";
        if (flight.getDepartureTime().isAfter(flight.getLandingTime()))
            errors += "Invalid Departure\n";
        if (flight.getSeats() < 0)
            errors += "Invalid number of seats";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
