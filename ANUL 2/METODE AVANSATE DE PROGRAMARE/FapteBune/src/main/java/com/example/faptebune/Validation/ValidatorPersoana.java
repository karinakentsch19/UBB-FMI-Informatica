package com.example.faptebune.Validation;

import com.example.faptebune.Domain.Persoana;

public class ValidatorPersoana implements AbstractValidator<Persoana> {
    @Override
    public void validate(Persoana persoana) {
        String errors = "";

        if (persoana.getNume().isEmpty())
            errors += "Nume invalid\n";
        if (persoana.getPrenume().isEmpty())
            errors += "Prenume invalid\n";
        if (persoana.getOras().isEmpty())
            errors += "Oras invalid\n";
        if (persoana.getNumarStrada().isEmpty())
            errors += "Numar strada invalid\n";
        if (persoana.getUsername().isEmpty())
            errors += "Username invalid\n";
        if (persoana.getParola().isEmpty())
            errors += "Parola invalid\n";
        if (persoana.getStrada().isEmpty())
            errors += "Strada invalid\n";
        if (persoana.getTelefon().isEmpty())
            errors += "Telefon invalid\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
