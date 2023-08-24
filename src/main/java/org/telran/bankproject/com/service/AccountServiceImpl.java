package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.CurrencyCode;
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
        Long lastProductId = productService.getAll().stream().map(Product::getId)
                .max(Comparator.naturalOrder()).orElse(null);
        Product product = productService.add(new Product(lastProductId + 1, account.getClient().getManager(), null,
                "Basic agreement", Status.ACTIVE, CurrencyCode.USD, 8, 500000,
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
        account.setAgreement(product.getAgreement());
        product.getAgreement().setAccount(account);
        product.getAgreement().setSum(account.getBalance());
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