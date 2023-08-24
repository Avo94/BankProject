package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.repository.ClientRepository;

import java.sql.Timestamp;
import java.util.Comparator;
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
        Long lastAccountId = accountService.getAll().stream().map(Account::getId)
                .max(Comparator.naturalOrder()).orElse(null);
        Account account = accountService.add(new Account(lastAccountId + 1, client, null, null,
                null, "Standard account", Type.STANDARD, Status.ACTIVE, 0,
                CurrencyCode.USD, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));
        client.setAccounts(List.of(account));
        return clientRepository.save(client);
    }

    @Override
    public void remove(long id) {
        clientRepository.deleteById(id);
    }
}