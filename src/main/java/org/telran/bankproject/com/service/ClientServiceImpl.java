package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.repository.ClientRepository;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public List<Client> getAll() {
        log.debug("Call method findAll");
        List<Client> all = clientRepository.findAll();

        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }

    @Override
    public Client getById(long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null)
            throw new EntityNotFoundException(String.format("Client with id %d not found", id));

        log.debug("Call method getReferenceById with id {}", id);
        return clientRepository.getReferenceById(id);
    }

    public Client getByLogin(String login) {
        return clientRepository.findByLogin(login);
    }

    @Override
    public Client add(Client client) {
        log.debug("Call method save with client {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        Client entity = getById(client.getId());

        if (client.getStatus() != null) entity.setStatus(client.getStatus());
        if (client.getTaxCode() != null) entity.setTaxCode(client.getTaxCode());
        if (client.getEmail() != null) entity.setEmail(client.getEmail());
        if (client.getAddress() != null) entity.setAddress(client.getAddress());
        if (client.getPhone() != null) entity.setPhone(client.getPhone());

        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.debug("Call method save with client {}", entity);
        return clientRepository.save(entity);
    }

    @Override
    public void remove(Client client) {
        Client entity = getById(client.getId());

        if (!entity.getAccounts().isEmpty()) {
            List<Account> accounts = entity.getAccounts();
            log.debug("Call method remove with accounts {}", accounts);
            accounts.forEach(x -> accountService.remove(x));
        }
        log.debug("Call method deleteAllByIdInBatch with client id {}", entity.getId());
        clientRepository.delete(client);
    }
}