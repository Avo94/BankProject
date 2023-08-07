package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client addClient(Client client);
}