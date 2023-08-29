package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.*;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.exceptions.NotEnoughMoneyException;
import org.telran.bankproject.com.repository.AccountRepository;
import org.telran.bankproject.com.service.converter.currency.CurrencyConverter;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public List<Account> getAll() {
        log.debug("Call method findAll");
        List<Account> all = accountRepository.findAll();
        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }

    @Override
    public Account getById(long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) throw new EntityNotFoundException(String.format("Client with id %d not found", id));
        log.debug("Call method getReferenceById with id {}", id);
        return accountRepository.getReferenceById(id);
    }

    @Override
    public List<Transaction> gatTransactions(String iban) {
        Account account = accountRepository.findAll().stream().filter(x -> x.getIban().equals(iban))
                .findFirst().orElse(null);
        if (account == null) throw new EntityNotFoundException(String.format("Account with iban %s not found", iban));
        List<Transaction> allTransactions = new ArrayList<>();
        log.debug("Call method getReferenceById for debitTransactions with id {}", account.getId());
        allTransactions.addAll(accountRepository.getReferenceById(account.getId()).getDebitTransactions());
        int size = allTransactions.size();
        log.debug("Method getReferenceById for debitTransactions returned {} transactions", size);
        log.debug("Call method getReferenceById for creditTransactions with id {}", account.getId());
        allTransactions.addAll(accountRepository.getReferenceById(account.getId()).getCreditTransactions());
        log.debug("Method getReferenceById for debitTransactions returned {} transactions",
                allTransactions.size() - size);
        return allTransactions;
    }

    @Override
    public double getBalance(String iban) {
        Account account = accountRepository.findAll().stream().filter(x -> x.getIban().equals(iban))
                .findFirst().orElse(null);
        if (account == null) throw new EntityNotFoundException(String.format("Account with iban %s not found", iban));
        log.debug("Call method getBalance with account {}", account);
        return accountRepository.findAll().stream()
                .filter(x -> x.getIban().equals(iban)).findFirst().map(Account::getBalance).get();
    }

    @Override
    @Transactional
    public Account add(Account account) {
        log.debug("Call method save with account {}", account);
        Account entity = accountRepository.save(account);
        entity.getClient().setAccounts(List.of(account));
        Long lastId;
        if (productService.getAll().isEmpty()) {
            lastId = 0L;
        } else {
            lastId = productService.getAll().stream().map(Product::getId)
                    .max(Comparator.naturalOrder()).orElse(null);
        }
        Product product = productService.add(new Product(lastId + 1, account.getClient().getManager(),
                null, account.getType() + " account", Status.ACTIVE, account.getCurrencyCode(),
                account.getType().getRate(), account.getType().getLimit(), new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));

        log.debug("Call method add with product {}", product);
        return entity;
    }

    @Override
    public double topUp(String iban, double amount) {
        Account account = accountRepository.findAll().stream().filter(x -> x.getIban().equals(iban))
                .findFirst().orElse(null);
        if (account == null) throw new EntityNotFoundException(String.format("Account with iban %s not found", iban));
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.debug("Call method save with balance {}", newBalance);
        accountRepository.save(account);
        return account.getBalance();
    }

    @Override
    public Account update(Account account) {
        Account entity = getById(account.getId());
        if (account.getName() != null) entity.setName(account.getName());
        if (account.getType() != null) entity.setType(account.getType());
        if (account.getStatus() != null) entity.setStatus(account.getStatus());
        if (account.getCurrencyCode() != null) {
            entity.setCurrencyCode(account.getCurrencyCode());
            entity.setBalance(converter.convert(entity, account, entity.getBalance()));
        }
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.debug("Call method save with account {}", entity);
        return accountRepository.save(entity);
    }

    @Override
    @Transactional
    public Transaction transferMoney(String iban1, String iban2, double amount) {
        Account debitAccount = accountRepository.findAll().stream()
                .filter(x -> x.getIban().equals(iban1)).findFirst().orElse(null);
        Account creditAccount = accountRepository.findAll().stream()
                .filter(x -> x.getIban().equals(iban2)).findFirst().orElse(null);
        if (debitAccount == null)
            throw new EntityNotFoundException(String.format("Account with iban %s not found", creditAccount.getIban()));
        if (creditAccount == null)
            throw new EntityNotFoundException(String.format("Account with iban %s not found", debitAccount.getIban()));
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
            log.debug("Call method save with account {}", debitAccount);
            accountRepository.save(debitAccount);
            log.debug("Call method save with account {}", creditAccount);
            accountRepository.save(creditAccount);
            Transaction transaction = transactionService.add(new Transaction(lastId + 1, debitAccount, creditAccount,
                    Type.SUCCESSFUL, amount, "Successful", new Timestamp(System.currentTimeMillis())));
            log.debug("Call method add with transaction {}", transaction);
            return transaction;
        } else {
            transactionService.add(new Transaction(lastId + 1, debitAccount, creditAccount, Type.FAILED,
                    amount, "Failed", new Timestamp(System.currentTimeMillis())));
            throw new NotEnoughMoneyException(String.format("Less money in account %s than %d",
                    debitAccount.getIban(), amount));
        }
    }

    @Override
    public void remove(Account account) {
        if (account.getAgreement() != null) {
            Product product = account.getAgreement().getProduct();
            log.debug("Call method remove with agreement {}", account.getAgreement());
            agreementService.remove(account.getAgreement());
            log.debug("Call method remove with product {}", product);
            productService.remove(product);
        }
        log.debug("Call method deleteAllByIdInBatch with account id {}", account.getId());
        accountRepository.deleteAllByIdInBatch(Collections.singleton(account.getId()));
    }
}