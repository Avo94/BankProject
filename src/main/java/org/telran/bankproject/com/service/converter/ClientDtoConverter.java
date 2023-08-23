package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Manager;

import java.sql.Timestamp;

@Component
public class ClientDtoConverter implements DtoConverter<Client, ClientDto> {

    @Override
    public ClientDto toDto(Client client) {
        return new ClientDto(client.getId(), new ManagerDto(client.getId(), null, null,
                client.getFirstName(), client.getLastName(), client.getStatus()), client.getAccounts()
                .stream().map(x -> new AccountDto(x.getId(), null, null,
                        null, null, x.getName(), x.getType(), x.getStatus(),
                        x.getBalance(), x.getCurrencyCode())).toList(), client.getStatus(),
                client.getTaxCode(), client.getFirstName(), client.getLastName(),
                client.getEmail(), client.getAddress(), client.getPhone());
    }

    @Override
    public Client toEntity(ClientDto client) {
        return new Client(client.getId(), new Manager(client.getManager().getId(), null, null,
                client.getManager().getFirstName(), client.getManager().getLastName(), client.getManager()
                .getStatus(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())),
                null, client.getStatus(), client.getTaxCode(), client.getFirstName(),
                client.getLastName(), client.getEmail(), client.getAddress(), client.getPhone(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}