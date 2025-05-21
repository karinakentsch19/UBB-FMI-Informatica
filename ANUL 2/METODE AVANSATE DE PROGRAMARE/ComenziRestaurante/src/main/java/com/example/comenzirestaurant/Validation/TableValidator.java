package com.example.comenzirestaurant.Validation;

import com.example.comenzirestaurant.Domain.Table;

public class TableValidator implements AbstractValidator<Table>{
    @Override
    public void validate(Table entity) {
        String errors = "";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
