package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(long id) {
        return transactionRepository.getReferenceById(id);
    }

    @Override
    public Transaction add(Transaction transaction) {
        transaction.getDebitAccount().setDebitTransactions(List.of(transaction));
        transaction.getCreditAccount().setCreditTransactions(List.of(transaction));
        return transactionRepository.save(transaction);
    }

    @Override
    public void remove(long id) {
        transactionRepository.deleteById(id);
    }
}
