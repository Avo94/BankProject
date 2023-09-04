package org.telran.bankproject.com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.exceptions.NotEnoughFieldsAreFilledException;
import org.telran.bankproject.com.service.ClientService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clients")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;
    @Autowired
    private DtoConverter<Client, ClientDto> clientConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<ClientDto> getAll() {
        log.debug("Call method getAll");
        return clientService.getAll().stream()
                .map(client -> clientConverter.toDto(client)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable(name = "id") long id) {
        log.debug("Call method getById with id {}", id);
        return clientConverter.toDto(clientService.getById(id));
    }

    @PostMapping
    public ClientDto addClient(@RequestBody ClientDto client) {
        if (client.getPassword() == null)
            throw new NotEnoughFieldsAreFilledException("The \"password\" field cannot be empty");
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        log.debug("Call method addClient with client {}", client);
        return clientConverter.toDto(clientService.add(clientConverter.toEntity(client)));
    }

    @PostMapping("/update")
    public ClientDto statusTaxCodeEmailAddressPhoneUpdate(@RequestBody ClientDto client) {
        log.debug("Call method statusTaxCodeEmailAddressPhoneUpdate with client {}", client);
        return clientConverter.toDto(clientService.update(new Client(client.getId(), null, null,
                client.getStatus(), client.getTaxCode(), null, null, null, null,
                client.getEmail(), client.getAddress(), client.getPhone(), null, null)));
    }

    @DeleteMapping
    public void remove(@RequestBody ClientDto client) {
        log.debug("Call method remove with client {}", client);
        clientService.remove(clientService.getById(client.getId()));
    }
}