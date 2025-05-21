package com.example.faptebune.Validation;

import com.example.faptebune.Domain.Nevoie;

public class NevoieValidator implements AbstractValidator<Nevoie>{
    @Override
    public void validate(Nevoie nevoie) {
        String errors = "";
        if (nevoie.getTitlu().isEmpty())
            errors += "Titlu invalid\n";

        if (nevoie.getDescriere().isEmpty())
            errors += "Descriere invalid\n";

        if (nevoie.getStatus().isEmpty())
            errors += "Status invalid\n";
        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
