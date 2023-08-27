package org.telran.bankproject.com.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.service.ManagerService;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ClientDtoConverter implements DtoConverter<Client, ClientDto> {

    @Autowired
    private ManagerService managerService;

    @Override
    public ClientDto toDto(Client client) {
        List<AccountDto> accounts;
        if (client.getAccounts() == null) {
            accounts = null;
        } else {
            accounts = client.getAccounts().stream().map(x -> new AccountDto(x.getId(), null, null,
                    null, null, x.getName(), x.getIban(), x.getType(), x.getStatus(),
                    x.getBalance(), x.getCurrencyCode())).toList();
        }
        return new ClientDto(client.getId(), new ManagerDto(client.getManager().getId(), null, null,
                client.getManager().getFirstName(), client.getManager().getLastName(), client.getManager().getStatus()),
                accounts, client.getStatus(), client.getTaxCode(), client.getFirstName(), client.getLastName(),
                client.getEmail(), client.getAddress(), client.getPhone());
    }

    @Override
    public Client toEntity(ClientDto client) {
        if (client.getId() > 0) {
            return new Client(client.getId(), null, null, client.getStatus(), client.getTaxCode(),
                    null, null, client.getEmail(), client.getAddress(), client.getPhone(),
                    null, null);
        }
        Manager manager = managerService.getAll().stream().filter(x -> x.getFirstName()
                .equals(client.getManager().getFirstName()) && x.getLastName()
                .equals(client.getManager().getLastName())).findFirst().orElse(null);
        return new Client(client.getId(), manager, null, client.getStatus(), client.getTaxCode(),
                client.getFirstName(), client.getLastName(), client.getEmail(), client.getAddress(), client.getPhone(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}