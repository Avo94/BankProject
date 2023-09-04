package org.telran.bankproject.com.service.generator;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ClientDto;

@Component
public class LoginGenerator {

    public String generate(ClientDto client) {
        return client.getFirstName().charAt(0) +
                client.getLastName() +
                client.getTaxCode().substring(0, 3);
    }
}