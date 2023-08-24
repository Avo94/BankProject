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

    @GetMapping("/transactions/{id}")
    public List<TransactionDto> gatTransactionHistory(@PathVariable(name = "id") long id) {
        List<TransactionDto> allTransactions = new ArrayList<>();
        accountService.getById(id).getDebitTransactions().stream().map(x -> transactionConverter.toDto(x))
                .forEach(allTransactions::add);
        accountService.getById(id).getCreditTransactions().stream().map(x -> transactionConverter.toDto(x))
                .forEach(allTransactions::add);
        return allTransactions;
    }

    @GetMapping("/balance/{id}")
    public double getBalance(@PathVariable(name = "id") long id) {
        return accountService.getById(id).getBalance();
    }

    @PostMapping
    public Account addAccount(@RequestBody AccountDto account) {
        return accountService.add(accountConverter.toEntity(account));
    }

    @PostMapping("/transfer/{id1}/{id2}/{amount}")
    public TransactionDto transferMoney(@PathVariable(name = "id1") long DebitId,
                                        @PathVariable(name = "id2") long CreditId,
                                        @PathVariable(name = "amount") double amount) {

        return transactionConverter.toDto(accountService.transferMoney(DebitId, CreditId, amount));
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable(name = "id") long id) {
        accountService.remove(id);
    }

}