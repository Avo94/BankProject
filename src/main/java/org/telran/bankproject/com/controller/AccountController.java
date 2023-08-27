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

    @GetMapping("/transactions/{iban}")
    public List<TransactionDto> gatTransactionHistory(@PathVariable String iban) {
        return accountService.gatTransactions(iban).stream().map(x->transactionConverter.toDto(x)).toList();
    }

    @GetMapping("/balance/{iban}")
    public double getBalance(@PathVariable String iban) {
        return accountService.getBalance(iban);
    }

    @PostMapping
    public AccountDto addAccount(@RequestBody AccountDto account) {
        return accountConverter.toDto(accountService.add(accountConverter.toEntity(account)));
    }

    @PostMapping("topup/{iban}/{amount}")
    public double topUpAccount(@PathVariable String iban, @PathVariable double amount) {
        return accountService.topUp(iban, amount);
    }

    @PostMapping("/update")
    public AccountDto nameTypeStatusCurrencyCodUpdate(@RequestBody AccountDto account) {
        return accountConverter.toDto(accountService.update(accountConverter.toEntity(account)));
    }

    @PostMapping("/transfer/{iban1}/{iban2}/{amount}")
    public TransactionDto transferMoney(@PathVariable(name = "iban1") String debitAccount,
                                        @PathVariable(name = "iban2") String creditAccount,
                                        @PathVariable double amount) {
        return transactionConverter.toDto(accountService.transferMoney(debitAccount, creditAccount, amount));
    }

    @DeleteMapping
    public void remove(@RequestBody AccountDto account) {
        accountService.remove(accountService.getById(account.getId()));
    }
}