package org.telran.bankproject.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.service.ClientService;
import org.telran.bankproject.com.service.converter.DtoConverter;
import org.telran.bankproject.com.service.security.ClientDetailService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @MockBean
    private ClientService clientService;
    @MockBean
    private DtoConverter<Client, ClientDto> clientConverter;
    @MockBean
    private ClientDetailService clientDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusTaxCodeEmailAddressPhoneUpdate() throws Exception {

        Client client = new Client(1, null, null, Status.ACTIVE, "tax code",
                "first name", "last name", "login", "password", "email",
                "address", "phone", null, null);
        ClientDto clientDto = new ClientDto(client.getId(), null, null, client.getStatus(),
                client.getTaxCode(), client.getFirstName(), client.getLastName(), client.getLogin(),
                client.getPassword(), client.getEmail(), client.getAddress(), client.getPhone());

        Mockito
                .when(clientService.update(client))
                .thenReturn(client);
        Mockito
                .when(clientConverter.toDto(client))
                .thenReturn(clientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(clientDto))));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}