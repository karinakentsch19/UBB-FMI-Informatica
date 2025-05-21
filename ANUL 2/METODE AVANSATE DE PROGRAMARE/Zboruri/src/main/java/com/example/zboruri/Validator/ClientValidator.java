package com.example.zboruri.Validator;

import com.example.zboruri.Domain.Client;

public class ClientValidator implements AbstractValidator<Client> {

    @Override
    public void validate(Client client) {
        String errors = "";
        if (client.getUsername().isEmpty())
            errors += "Invalid username\n";
        if(client.getName().isEmpty())
            errors += "Invalid name\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
