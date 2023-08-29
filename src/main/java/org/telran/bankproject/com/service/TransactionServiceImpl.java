package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.repository.TransactionRepository;

import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public List<Transaction> getAll() {
        log.debug("Call method findAll");
        List<Transaction> all = transactionRepository.findAll();
        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }

    @Override
    public Transaction getById(long id) {
        log.debug("Call method getReferenceById with id {}", id);
        return transactionRepository.getReferenceById(id);
    }

    @Override
    public Transaction add(Transaction transaction) {
         transaction.getDebitAccount().setDebitTransactions(List.of(transaction));
        transaction.getCreditAccount().setCreditTransactions(List.of(transaction));
        log.debug("Call method save with transaction {}", transaction);
        return transactionRepository.save(transaction);
    }

    @Override
    public void remove(Transaction transaction) {
        log.debug("Call method deleteAllByIdInBatch with transaction id {}", transaction.getId());
        transactionRepository.deleteAllByIdInBatch(Collections.singleton(transaction.getId()));
    }
}
