package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.repository.ClientRepository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountService accountService;

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
    public Client update(Client client) {
        Client entity = clientRepository.getReferenceById(client.getId());
        if (client.getStatus() != null) entity.setStatus(client.getStatus());
        if (client.getTaxCode() != null) entity.setTaxCode(client.getTaxCode());
        if (client.getEmail() != null) entity.setEmail(client.getEmail());
        if (client.getAddress() != null) entity.setAddress(client.getAddress());
        if (client.getPhone() != null) entity.setPhone(client.getPhone());
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return clientRepository.save(entity);
    }

    @Override
    public void remove(Client client) {
        client.getAccounts().forEach(x -> accountService.remove(x));
        clientRepository.deleteAllByIdInBatch(Collections.singleton(client.getId()));
    }
}