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
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.enums.Status;
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
    private DtoConverter<Client, ClientDto> clientConverter;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusTaxCodeEmailAddressPhoneUpdate() throws Exception {
        Client client = new Client(1L, new Manager(), List.of(new Account()), Status.ACTIVE, "tax code",
                "first name", "last name", "login", "password", "email",
                "address", "phone", null, null);
        ClientDto clientDto = new ClientDto(client.getId(), new ManagerDto(), List.of(new AccountDto()), client.getStatus(),
                client.getTaxCode(), client.getFirstName(), client.getLastName(), client.getLogin(),
                client.getPassword(), client.getEmail(), client.getAddress(), client.getPhone());

        Mockito.when(clientConverter.toEntity(clientDto)).thenReturn(client);
        Mockito.when(clientService.update(client)).thenReturn(client);
        Mockito.when(clientConverter.toDto(client)).thenReturn(clientDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients/update")
                        .content(asJsonString(clientDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(clientDto)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}