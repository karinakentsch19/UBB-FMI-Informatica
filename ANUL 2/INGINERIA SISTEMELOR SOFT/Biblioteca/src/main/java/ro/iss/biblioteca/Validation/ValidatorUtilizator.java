package ro.iss.biblioteca.Validation;

import ro.iss.biblioteca.Domain.Utilizator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtilizator {
    public void validate(Utilizator utilizator) {
        String errors = "";

        if (utilizator.getNume().isEmpty())
            errors += "Nume invalid\n";
        if (utilizator.getPrenume().isEmpty())
            errors += "Prenume invalid\n";
        if(utilizator.getCnp().toString().length() != 13)
            errors += "Cnp invalid\n";
        if(utilizator.getAdresa().isEmpty())
            errors += "Adresa invalida\n";

        String regexEmail = "^.+@.+\\..+$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(utilizator.getEmail());
        if(!matcher.find())
            errors += "Email invalid\n";

        if(utilizator.getTelefon().isEmpty())
            errors += "Telefon invalid\n";
        if(utilizator.getParola().isEmpty())
            errors += "Parola invalida\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
