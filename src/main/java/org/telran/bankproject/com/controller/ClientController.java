package org.telran.bankproject.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    List<Client> getAll() {
        return clientService.getAll();
    }

    @PostMapping
    Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }
}