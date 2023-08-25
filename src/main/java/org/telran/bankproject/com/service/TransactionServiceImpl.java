package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.repository.TransactionRepository;

import java.sql.Timestamp;
import java.util.Comparator;
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
    public Transaction create(Account senderAccount, Account recipientAccount, double amount, String description) {
        Long lastId;
        if (transactionRepository.findAll() == null) {
            lastId = null;
        } else {
            lastId = transactionRepository.findAll().stream().map(Transaction::getId)
                    .max(Comparator.naturalOrder()).get();
        }
        return transactionRepository.save(new Transaction(lastId + 1, senderAccount, recipientAccount, Type.SUCCESSFUL,
                amount, description, new Timestamp(System.currentTimeMillis())));
    }

    @Override
    public void remove(long id) {
        transactionRepository.deleteById(id);
    }
}
