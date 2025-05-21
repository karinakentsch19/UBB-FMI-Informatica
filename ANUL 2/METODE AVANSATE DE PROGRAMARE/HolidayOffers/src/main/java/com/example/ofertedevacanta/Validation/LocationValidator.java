package com.example.ofertedevacanta.Validation;

import com.example.ofertedevacanta.Domain.Location;

public class LocationValidator implements AbstractValidator<Location> {
    @Override
    public void validate(Location entity) {
        String errors = "";

        if (entity.getLocationName().isEmpty())
            errors += "Invalid name\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
