package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.repository.AccountRepository;

import java.sql.Timestamp;
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
        Account entity = accountRepository.save(account);
        Long lastProductId;
        Long lastAgreementId;
        if (productService.getAll() == null) {
            lastProductId = 1L;
        } else {
            lastProductId = productService.getAll().stream().map(Product::getId)
                    .max(Comparator.naturalOrder()).orElse(null);
        }
        if (agreementService.getAll() == null) {
            lastAgreementId = 1L;
        } else {
            lastAgreementId = agreementService.getAll().stream().map(Agreement::getId)
                    .max(Comparator.naturalOrder()).orElse(null);
        }
        Product product = productService.add(new Product(lastProductId + 1, account.getClient().getManager(),
                null, account.getType() + " account", Status.ACTIVE, account.getCurrencyCode(),
                account.getType().getRate(), account.getType().getLimit(), new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));
        Agreement agreement = agreementService.add(new Agreement(lastAgreementId + 1, entity, product, account
                .getType().getRate(), Status.ACTIVE, account.getBalance(), new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));
        account.setAgreement(agreement);
        return accountRepository.save(account);
    }

    @Override
    public Transaction transferMoney(long id1, long id2, double amount) {
        Account debitAccount = accountRepository.getReferenceById(id1);
        Account creditAccount = accountRepository.getReferenceById(id2);
        if (debitAccount.getBalance() < amount) {
            debitAccount.setBalance(debitAccount.getBalance() - amount);
            creditAccount.setBalance(creditAccount.getBalance() + amount);
            accountRepository.save(debitAccount);
            accountRepository.save(creditAccount);
            return transactionService.create(debitAccount, creditAccount, amount, "Successful");
        } else {
            transactionService.create(debitAccount, creditAccount, amount, "Failed");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void remove(long id) {
        accountRepository.deleteById(id);
    }
}