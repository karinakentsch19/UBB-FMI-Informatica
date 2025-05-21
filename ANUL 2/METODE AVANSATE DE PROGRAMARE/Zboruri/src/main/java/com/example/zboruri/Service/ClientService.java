package com.example.zboruri.Service;

import com.example.zboruri.Domain.Client;
import com.example.zboruri.Repository.ClientRepository;

import java.util.Optional;


public class ClientService extends AbstractService{
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findClientByUsername(String username){
        Optional<Client> optionalClient = clientRepository.findClient(username);
        if (optionalClient.isPresent())
            return optionalClient.get();
        else
            throw new RuntimeException("Client doesn't exist\n");
    }
}
