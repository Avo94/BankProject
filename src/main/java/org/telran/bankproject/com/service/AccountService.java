package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(long id);

    Account add(Account account);

    Transaction transferMoney(long id1, long id2, double amount);

    void remove(long id);
}