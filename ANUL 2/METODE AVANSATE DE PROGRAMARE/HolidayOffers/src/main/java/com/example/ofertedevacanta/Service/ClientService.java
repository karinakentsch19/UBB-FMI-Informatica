package com.example.ofertedevacanta.Service;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Repository.ClientRepository;
import com.example.ofertedevacanta.Utils.Observable;

import java.util.Optional;

public class ClientService extends AbstractService{
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findClient(Long clientId){
        Optional<Client> client = clientRepository.find(clientId);
        if (client.isEmpty())
            throw new RuntimeException("Client doesn't exist\n");
        return client.get();
    }
}
