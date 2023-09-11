package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.bankproject.com.entity.*;
import org.telran.bankproject.com.repository.AccountRepository;
import org.telran.bankproject.com.service.converter.currency.CurrencyConverter;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AgreementService agreementService;

    @Override
    public List<Account> getAll() {
        log.debug("Call method findAll");
        List<Account> all = accountRepository.findAll();
        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }

    @Override
    public Account getById(long id) {
        accountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Client with id %d not found", id)));
        log.debug("Call method getReferenceById with id {}", id);
        return accountRepository.getReferenceById(id);
    }

    @Override
    public Account getByIban(String iban) {
        log.debug("Call method findByIban with iban {}", iban);
        return accountRepository.findByIban(iban).orElseThrow(() ->
                new EntityNotFoundException(String.format("Account with iban %s not found", iban)));
    }

    @Override
    public List<Transaction> getTransactions(String iban) {
        Account account = getByIban(iban);

        log.debug("Call method getReferenceById for debitTransactions with id {}", account.getId());
        List<Transaction> allTransactions = new ArrayList<>(account.getDebitTransactions());
        int oldSize = allTransactions.size();
        log.debug("Method getReferenceById for debitTransactions returned {} transactions", oldSize);

        log.debug("Call method getReferenceById for creditTransactions with id {}", account.getId());
        allTransactions.addAll(account.getCreditTransactions());
        log.debug("Method getReferenceById for debitTransactions returned {} transactions",
                allTransactions.size() - oldSize);
        return allTransactions;
    }

    @Override
    public double getBalance(String iban) {
        Account account = getByIban(iban);
        log.debug("Call method getBalance with account {}", account);
        return account.getBalance().doubleValue();
    }

    @Override
    public Account add(Account account) {
        log.debug("Call method save with account {}", account);
        return accountRepository.save(account);
    }

    @Override
    public double topUp(String iban, double amount) {
        Account account = getByIban(iban);
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!login.equals(account.getClient().getLogin()))
            throw new UnsupportedOperationException("The operation is allowed to be carried out only on own accounts");

        BigDecimal newBalance = account.getBalance().add(BigDecimal.valueOf(amount));
        account.setBalance(newBalance);
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        log.debug("Call method save with balance {}", newBalance);
        accountRepository.save(account);
        return account.getBalance().doubleValue();
    }

    @Override
    public Account update(Account account) {
        Account entity = getByIban(account.getIban());

        if (account.getName() != null) entity.setName(account.getName());
        if (account.getType() != null) entity.setType(account.getType());
        if (account.getStatus() != null) entity.setStatus(account.getStatus());
        if (account.getCurrencyCode() != null) {
            entity.setCurrencyCode(account.getCurrencyCode());
            entity.setBalance(CurrencyConverter.convert(entity, account, entity.getBalance()));
        }

        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.debug("Call method save with account {}", entity);
        return accountRepository.save(entity);
    }

    @Transactional
    @Override
    public void remove(Account account) {
        Account entity = getById(account.getId());

        if (!entity.getDebitTransactions().isEmpty())
            entity.getDebitTransactions().forEach(x -> x.setDebitAccount(null));
        if (!entity.getCreditTransactions().isEmpty())
            entity.getCreditTransactions().forEach(x -> x.setCreditAccount(null));

        if (entity.getAgreement() != null) {
            log.debug("Call method remove with agreement {}", entity.getAgreement());
            agreementService.remove(entity.getAgreement());
        } else {
            log.debug("Call method delete with account id {}", entity.getId());
            accountRepository.delete(entity);
        }
    }
}