package com.example.ofertedevacanta.Validation;

import com.example.ofertedevacanta.Domain.SpecialOffer;

public class SpecialOfferValidator implements AbstractValidator<SpecialOffer>{
    @Override
    public void validate(SpecialOffer entity) {
        String errors = "";

        if (entity.getPercents() < 1 || entity.getPercents() > 100)
            errors += "Invalid percents\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
