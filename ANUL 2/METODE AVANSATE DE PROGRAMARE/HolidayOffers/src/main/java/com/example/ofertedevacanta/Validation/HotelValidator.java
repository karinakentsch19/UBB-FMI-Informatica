package com.example.ofertedevacanta.Validation;

import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Utils.Type;

public class HotelValidator implements AbstractValidator<Hotel> {
    @Override
    public void validate(Hotel entity) {
        String errors = "";

        if (entity.getHotelName().isEmpty())
            errors += "Invalid hotel name\n";

        if (entity.getNoRooms() <= 0)
            errors += "Invalid no of rooms\n";

        if (entity.getPricePerNight() < 0)
            errors += "Invalid price\n";

        if (entity.getType() != Type.family || entity.getType() != Type.teenagers || entity.getType() != Type.oldPeople)
            errors += "Invalid type\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
