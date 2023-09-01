package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client getById(long id);

    Client getByLogin(String login);

    Client add(Client client);

    Client update(Client client);

    void remove(Client client);
}