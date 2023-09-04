package org.telran.bankproject.com.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.service.ManagerService;
import org.telran.bankproject.com.service.generator.LoginGenerator;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ClientDtoConverter implements DtoConverter<Client, ClientDto> {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private LoginGenerator loginGenerator;

    @Override
    public ClientDto toDto(Client client) {
        List<AccountDto> accounts;

        if (client.getAccounts() == null) accounts = null;
        else accounts = client.getAccounts().stream().map(x -> new AccountDto(x.getId(), null, null,
                null, null, x.getName(), x.getIban(), x.getType(), x.getStatus(),
                x.getBalance(), x.getCurrencyCode())).toList();

        return new ClientDto(client.getId(),
                new ManagerDto(client.getManager().getId(), null, null, client.getManager()
                        .getFirstName(), client.getManager().getLastName(), client.getManager().getStatus()),
                accounts, client.getStatus(), client.getTaxCode(), client.getFirstName(), client.getLastName(),
                client.getLogin(), null, client.getEmail(), client.getAddress(), client.getPhone());
    }

    @Override
    public Client toEntity(ClientDto client) {
        Manager manager = null;
        if (client.getManager() != null) manager = managerService.getAll().stream().filter(x -> x.getFirstName()
                .equals(client.getManager().getFirstName()) && x.getLastName().equals(client.getManager()
                .getLastName())).findFirst().orElse(null);

        if (manager == null) throw new EntityNotFoundException("Such manager was not found in the database");
        return new Client(client.getId(), manager, null, client.getStatus(), client.getTaxCode(),
                client.getFirstName(), client.getLastName(), loginGenerator.generate(client),
                client.getPassword(), client.getEmail(), client.getAddress(), client.getPhone(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}