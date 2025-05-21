package com.example.zboruri.Validator;

import com.example.zboruri.Domain.Ticket;

public class TicketValidator implements AbstractValidator<Ticket> {
    @Override
    public void validate(Ticket ticket) {
        String errors = "";
        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
