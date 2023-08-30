package org.telran.bankproject.com.service;

import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(long id);

    Account getByIban(String iban);

    List<Transaction> gatTransactions(String iban);

    double getBalance(String iban);

    Account add(Account account);

    double topUp(String iban, double amount);

    Account update(Account account);

    void remove(Account account);
}