package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.entity.Client;

import java.sql.Timestamp;

@Component
public class ClientDtoConverter implements DtoConverter<Client, ClientDto> {

    @Override
    public ClientDto toDto(Client client) {
        return new ClientDto(client.getId(), client.getManagerId(), client.getAccounts(),
                client.getStatus(), client.getTaxCode(), client.getFirstName(), client.getLastName(),
                client.getEmail(), client.getAddress(), client.getPhone());
    }

    @Override
    public Client toEntity(ClientDto client) {
        return new Client(client.getId(), client.getManagerId(), client.getAccounts(),
                client.getStatus(), client.getTaxCode(), client.getFirstName(),
                client.getLastName(), client.getEmail(), client.getAddress(), client.getPhone(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}