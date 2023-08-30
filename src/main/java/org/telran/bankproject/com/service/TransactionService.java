package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAll();

    Transaction getById(long id);

    Transaction add(Transaction transaction);

    Transaction transfer(String iban1, String iban2, double amount);

    void remove(Transaction transaction);
}