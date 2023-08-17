package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.entity.Client;

@Component
public class ClientDtoConverter implements DtoConverter<Client, ClientDto> {

    @Override
    public ClientDto toDto(Client client) {
        return new ClientDto(client.getId(), client.getStatus(), client.getFirstName(), client.getLastName(), client.getEmail());
    }

    @Override
    public Client toEntity(ClientDto clientDto) {
        throw new UnsupportedOperationException();
    }
}