package com.example.ofertedevacanta.Validation;

import com.example.ofertedevacanta.Domain.Client;

public class ClientValidator implements AbstractValidator<Client> {
    @Override
    public void validate(Client entity) {
        String errors = "";
        if (entity.getName().isEmpty())
            errors += "Invalid name\n";

        if (entity.getFidelityGrade() < 1 || entity.getFidelityGrade() > 100)
            errors += "Invalid fidelity grade\n";

        if (entity.getAge() < 0)
            errors += "Invalid age\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
