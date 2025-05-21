package com.example.ofertedevacanta.Validation;

import com.example.ofertedevacanta.Domain.Reservation;

public class ReservationValidator implements AbstractValidator<Reservation> {
    @Override
    public void validate(Reservation entity) {
        String errors = "";

        if (entity.getNoNights() < 0)
            errors += "Invalid no of nights\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
