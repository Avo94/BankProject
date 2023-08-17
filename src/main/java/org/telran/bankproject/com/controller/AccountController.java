package org.telran.bankproject.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.service.AccountService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DtoConverter<Account, AccountDto> converter;

    @GetMapping
    public List<AccountDto> getAll() {
        return accountService.getAll().stream().map(account -> converter.toDto(account)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable(name = "id") long id) {
        return converter.toDto(accountService.getById(id));
    }

    @PostMapping
    public Account addClient(@RequestBody Account account) {
        return accountService.add(account);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable(name = "id") long id) {
        accountService.remove(id);
    }
}