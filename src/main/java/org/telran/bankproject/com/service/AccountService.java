package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(long id);

    double getBalance(String iban);

    Account add(Account account);

    double topUp(String iban, double amount);

    Account update(Account account);

    Transaction transferMoney(String iban1, String iban2, double amount);

    void remove(Account account);
}