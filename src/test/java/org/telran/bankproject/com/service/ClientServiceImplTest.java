package org.telran.bankproject.com.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.repository.ClientRepository;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientService clientService = new ClientServiceImpl();

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void init() {
        Client client = new Client();
        client.setLogin("AAllova112");
        Mockito
                .when(clientRepository.findByLogin(client.getLogin()))
                .thenReturn(client);
    }

    @Test
    void getByLogin() {
        Client aAllova112 = clientService.getByLogin("AAllova112");
        assertEquals("AAllova112", aAllova112.getLogin());
    }
}