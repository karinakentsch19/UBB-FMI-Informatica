package com.example.trenuri.Validator;

import com.example.trenuri.Domain.City;

public class CityValidator implements AbstractValidator<City> {
    @Override
    public void validate(City entity) {
        String errors = "";

        if (entity.getName().isEmpty())
            errors += "Invalid name\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
