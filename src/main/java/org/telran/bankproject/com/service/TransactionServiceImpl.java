package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.exceptions.NotEnoughMoneyException;
import org.telran.bankproject.com.repository.TransactionRepository;
import org.telran.bankproject.com.service.converter.currency.CurrencyConverter;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public List<Transaction> getAll() {
        log.debug("Call method findAll");
        List<Transaction> all = transactionRepository.findAll();
        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }

    @Override
    public Transaction getById(long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null)
            throw new EntityNotFoundException(String.format("Transaction with id %d not found", id));
        log.debug("Call method getReferenceById with id {}", id);
        return transactionRepository.getReferenceById(id);
    }

    @Override
    public Transaction add(Transaction transaction) {
        log.debug("Call method save with transaction {}", transaction);
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction transfer(String iban1, String iban2, double amount) {
        Account debitAccount = accountService.getAll().stream()
                .filter(x -> x.getIban().equals(iban1)).findFirst().orElse(null);
        Account creditAccount = accountService.getAll().stream()
                .filter(x -> x.getIban().equals(iban2)).findFirst().orElse(null);
        if (debitAccount == null)
            throw new EntityNotFoundException(String.format("Account with iban %s not found", creditAccount.getIban()));
        if (creditAccount == null)
            throw new EntityNotFoundException(String.format("Account with iban %s not found", debitAccount.getIban()));
        long lastId;
        if (transactionRepository.findAll().isEmpty()) {
            lastId = 0L;
        } else {
            lastId = transactionRepository.findAll().stream().map(Transaction::getId)
                    .max(Comparator.naturalOrder()).get();
        }
        if (debitAccount.getBalance() < amount) {
            transactionRepository.save(new Transaction(lastId + 1, debitAccount, creditAccount, Type.FAILED,
                    amount, "Failed", new Timestamp(System.currentTimeMillis())));
            throw new NotEnoughMoneyException(String.format("Less money in account %s than %f",
                    debitAccount.getIban(), amount));
        }
        debitAccount.setBalance(debitAccount.getBalance() - amount);
        creditAccount.setBalance(creditAccount.getBalance() +
                CurrencyConverter.convert(debitAccount, creditAccount, amount));
        debitAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        creditAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.debug("Call method add with account {}", debitAccount);
        accountService.add(debitAccount);
        log.debug("Call method add with account {}", creditAccount);
        accountService.add(creditAccount);
        Transaction transaction = transactionRepository.save(new Transaction(lastId + 1, debitAccount, creditAccount,
                Type.SUCCESSFUL, amount, "Successful", new Timestamp(System.currentTimeMillis())));
        log.debug("Call method save with transaction {}", transaction);
        return transaction;
    }

    @Override
    public void remove(Transaction transaction) {
        Transaction entity = getById(transaction.getId());
        log.debug("Call method deleteAllByIdInBatch with transaction id {}", transaction.getId());
        transactionRepository.deleteById(entity.getId());
    }
}