package org.telran.bankproject.com.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.service.AccountService;

import java.util.List;

@Component
public class IbanGenerator {

    @Autowired
    private AccountService accountService;

    public String generate() {
        StringBuilder ibanConstructor = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int digit = (int) (Math.random() * 10 - 1);
            ibanConstructor.append(digit);
        }
        String currentIban = ibanConstructor.toString();
        List<String> allIbans = accountService.getAll().stream().map(Account::getIban).toList();
        for (String existingIban : allIbans) {
            if (existingIban.equals(currentIban)) {
                currentIban = generate();
            }
        }
        return currentIban;
    }
}