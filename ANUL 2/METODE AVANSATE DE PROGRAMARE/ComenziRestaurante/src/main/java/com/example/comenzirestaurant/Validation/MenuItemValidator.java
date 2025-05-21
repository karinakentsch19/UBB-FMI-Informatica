package com.example.comenzirestaurant.Validation;

import com.example.comenzirestaurant.Domain.MenuItem;

public class MenuItemValidator implements AbstractValidator<MenuItem>{
    @Override
    public void validate(MenuItem entity) {
        String errors = "";

        if (entity.getCategory().isEmpty())
            errors += "Invalid category\n";

        if (entity.getItem().isEmpty())
            errors += "Invalid item\n";

        if (entity.getPrice() < 0)
            errors += "Invalid price\n";

        if (entity.getCurrency().isEmpty())
            errors += "Invalid currency\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
