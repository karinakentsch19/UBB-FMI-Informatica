package com.example.comenzirestaurant.Validation;

import com.example.comenzirestaurant.Domain.Order;
import com.example.comenzirestaurant.Utils.OrderStatus;

public class OrderValidator implements AbstractValidator<Order>{
    @Override
    public void validate(Order entity) {
        String errors = "";

        if (entity.getStatus() != OrderStatus.PLACED && entity.getStatus() != OrderStatus.PREPARING && entity.getStatus() != OrderStatus.SERVED)
            errors += "Invalid status\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
