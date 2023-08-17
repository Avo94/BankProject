package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(long id);

    Account add(Account account);

    void remove(long id);
}