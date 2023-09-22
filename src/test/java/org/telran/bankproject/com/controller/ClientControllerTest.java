package org.telran.bankproject.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.*;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.repository.ClientRepository;
import org.telran.bankproject.com.service.ClientService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @MockBean
    private ClientService clientService;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private DtoConverter<Client, ClientDto> clientConverter;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusTaxCodeEmailAddressPhoneUpdate() throws Exception {
        ClientDto inputClient = new ClientDto(1, new ManagerDto(), List.of(new AccountDto()),
                Status.ACTIVE, "12345", "Maxim", "Maximov",
                "Max123", "12345678", "max@gmail.com", "12 Street", "+12345678");

        Client client = new Client(inputClient.getId(), new Manager(), List.of(new Account()),
                inputClient.getStatus(), inputClient.getTaxCode(), inputClient.getFirstName(),
                inputClient.getLastName(), inputClient.getLogin(), inputClient.getPassword(),
                inputClient.getEmail(), inputClient.getAddress(), inputClient.getPhone(),
                null, null);

        ClientDto outputClient = new ClientDto(client.getId(), inputClient.getManager(), inputClient.getAccounts(),
                client.getStatus(), client.getTaxCode(), client.getFirstName(), client.getLastName(),
                client.getLogin(), client.getPassword(), client.getEmail(), client.getAddress(), client.getPhone());

        Mockito.when(clientConverter.toEntity(inputClient)).thenReturn(client);
        Mockito.when(clientService.update(Mockito.any(Client.class))).thenReturn(client);
        Mockito.when(clientConverter.toDto(client)).thenReturn(outputClient);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients/update")
                        .content(asJsonString(inputClient))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(outputClient)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}