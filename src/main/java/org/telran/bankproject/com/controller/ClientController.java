package org.telran.bankproject.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.service.ClientService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DtoConverter<Client, ClientDto> clientConverter;

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAll().stream().map(client -> clientConverter.toDto(client)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable(name = "id") long id) {
        return clientConverter.toDto(clientService.getById(id));
    }

    @PostMapping
    public ClientDto addClient(@RequestBody ClientDto client) {
        return clientConverter.toDto(clientService.add(clientConverter.toEntity(client)));
    }

    @PostMapping("/update")
    public ClientDto statusTaxCodeEmailAddressPhoneUpdate(@RequestBody ClientDto client) {
        return clientConverter.toDto(clientService.update(clientConverter.toEntity(client)));
    }

    @DeleteMapping
    public void remove(@RequestBody ClientDto client) {
        clientService.remove(clientService.getById(client.getId()));
    }
}