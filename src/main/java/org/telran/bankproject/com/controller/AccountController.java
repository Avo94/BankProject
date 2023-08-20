package org.telran.bankproject.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.service.AccountService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DtoConverter<Account, AccountDto> accountConverter;

    @Autowired
    private DtoConverter<Transaction, TransactionDto> transactionConverter;

    @GetMapping
    public List<AccountDto> getAll() {
        return accountService.getAll().stream().map(account -> accountConverter.toDto(account))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable(name = "id") long id) {
        return accountConverter.toDto(accountService.getById(id));
    }

    @GetMapping("/transaction_history/{id}")
    public List<TransactionDto> gatTransactionHistory(@PathVariable(name = "id") long id) {
        List<TransactionDto> allTransactions = new ArrayList<>();
        accountService.getById(id).getDebitTransaction().stream().map(x -> allTransactions
                .add(transactionConverter.toDto(x))).toList();
        accountService.getById(id).getCreditTransaction().stream().map(x -> allTransactions
                .add(transactionConverter.toDto(x))).toList();
        return allTransactions;
    }

    @PostMapping
    public Account addClient(@RequestBody AccountDto account) {
        return accountService.add(accountConverter.toEntity(account));
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable(name = "id") long id) {
        accountService.remove(id);
    }
}