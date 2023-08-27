package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.*;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.repository.AccountRepository;
import org.telran.bankproject.com.service.converter.currency.CurrencyConverter;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AgreementService agreementService;
    @Autowired
    private CurrencyConverter converter;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(long id) {
        return accountRepository.getReferenceById(id);
    }

    @Override
    public double getBalance(String iban) {
        return accountRepository.findAll().stream()
                .filter(x -> x.getIban().equals(iban)).findFirst().map(Account::getBalance).get();
    }

    @Override
    public Account add(Account account) {
        Account entity = accountRepository.save(account);
        entity.getClient().setAccounts(List.of(account));
        Long lastId;
        if (productService.getAll().isEmpty()) {
            lastId = 0L;
        } else {
            lastId = productService.getAll().stream().map(Product::getId)
                    .max(Comparator.naturalOrder()).orElse(null);
        }
        productService.add(new Product(lastId + 1, account.getClient().getManager(),
                null, account.getType() + " account", Status.ACTIVE, account.getCurrencyCode(),
                account.getType().getRate(), account.getType().getLimit(), new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));
        return entity;
    }

    @Override
    public double topUp(String iban, double amount) {
        Account account = accountRepository.findAll().stream().filter(x -> x.getIban().equals(iban)).findFirst().get();
        account.setBalance(account.getBalance() + amount);
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accountRepository.save(account);
        return account.getBalance();
    }

    @Override
    public Account update(Account account) {
        Account entity = accountRepository.findAll().stream().filter(x -> x.getIban()
                .equals(account.getIban())).findFirst().get();
        if (account.getName() != null) entity.setName(account.getName());
        if (account.getType() != null) entity.setType(account.getType());
        if (account.getStatus() != null) entity.setStatus(account.getStatus());
        if (account.getCurrencyCode() != null) {
            entity.setCurrencyCode(account.getCurrencyCode());
            entity.setBalance(converter.convert(entity, account, entity.getBalance()));
        }
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return accountRepository.save(entity);
    }

    @Override
    public Transaction transferMoney(String iban1, String iban2, double amount) {
        Account debitAccount = accountRepository.findAll().stream()
                .filter(x -> x.getIban().equals(iban1)).findFirst().get();
        Account creditAccount = accountRepository.findAll().stream()
                .filter(x -> x.getIban().equals(iban2)).findFirst().get();
        long lastId;
        if (transactionService.getAll().isEmpty()) {
            lastId = 0L;
        } else {
            lastId = transactionService.getAll().stream().map(Transaction::getId)
                    .max(Comparator.naturalOrder()).get();
        }
        if (debitAccount.getBalance() >= amount) {
            debitAccount.setBalance(debitAccount.getBalance() - amount);
            creditAccount.setBalance(creditAccount.getBalance() +
                    converter.convert(debitAccount, creditAccount, amount));
            debitAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            creditAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            accountRepository.save(debitAccount);
            accountRepository.save(creditAccount);
            return transactionService.add(new Transaction(lastId + 1, debitAccount, creditAccount,
                    Type.SUCCESSFUL, amount, "Successful", new Timestamp(System.currentTimeMillis())));
        } else {
            transactionService.add(new Transaction(lastId + 1, debitAccount, creditAccount, Type.FAILED,
                    amount, "Failed", new Timestamp(System.currentTimeMillis())));
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void remove(Account account) {
        if (account.getAgreement() != null) {
            Product product = account.getAgreement().getProduct();
            agreementService.remove(account.getAgreement());
            productService.remove(product);
        }
        accountRepository.deleteAllByIdInBatch(Collections.singleton(account.getId()));
    }
}