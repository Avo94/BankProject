package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.repository.AccountRepository;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(long id) {
        return accountRepository.getReferenceById(id);
    }

    @Override
    public Account add(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void remove(long id) {
        accountRepository.deleteById(id);
    }
}