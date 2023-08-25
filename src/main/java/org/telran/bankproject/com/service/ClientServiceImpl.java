package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.repository.ClientRepository;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(long id) {
        return clientRepository.getReferenceById(id);
    }

    @Override
    public Client add(Client client) {
        client.getManager().setClients(List.of(client));
        return clientRepository.save(client);
    }

    @Override
    public void remove(long id) {
        clientRepository.deleteById(id);
    }
}