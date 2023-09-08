package org.telran.bankproject.com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.*;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.service.AccountService;
import org.telran.bankproject.com.service.AgreementService;
import org.telran.bankproject.com.service.TransactionService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private DtoConverter<Account, AccountDto> accountConverter;
    @Autowired
    private DtoConverter<Transaction, TransactionDto> transactionConverter;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AgreementService agreementService;

    @GetMapping
    public List<AccountDto> getAll() {

        log.debug("Call method getAll");
        return accountService.getAll().stream()
                .map(account -> accountConverter.toDto(account)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable(name = "id") long id) {
        log.debug("Call method getById with id {}", id);
        return accountConverter.toDto(accountService.getById(id));
    }

    @GetMapping("/transactions/{iban}")
    public List<TransactionDto> getTransactionHistory(@PathVariable String iban) {
        log.debug("Call method getTransactionHistory with iban {}", iban);
        return accountService.getTransactions(iban).stream()
                .map(x -> transactionConverter.toDto(x)).toList();
    }

    @GetMapping("/balance/{iban}")
    public double getBalance(@PathVariable String iban) {
        log.debug("Call method getBalance with iban {}", iban);
        return accountService.getBalance(iban);
    }

    @PostMapping
    public AccountDto addAccount(@RequestBody AccountDto accountdto) {
        Account account = accountConverter.toEntity(accountdto);

        Product product = new Product(0, account.getClient().getManager(),
                null, account.getType() + " ACCOUNT", Status.ACTIVE,
                account.getCurrencyCode(), account.getType().getRate(), account.getType().getLimit(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        Agreement agreement = new Agreement(0, account, product, product.getInterestRate(),
                Status.ACTIVE, account.getBalance().doubleValue(), new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        log.debug("Call method add with agreement {}", agreement);
        Agreement entityAgreement = agreementService.add(agreement);
        entityAgreement.getAccount().setAgreement(entityAgreement);

        return accountConverter.toDto(entityAgreement.getAccount());
    }

    @PostMapping("topup/{iban}/{amount}")
    public double topUpAccount(@PathVariable String iban, @PathVariable double amount) {
        log.debug("Call method topUpAccount with iban {}, and {}", iban, amount);
        return accountService.topUp(iban, amount);
    }

    @PostMapping("/update")
    public AccountDto nameTypeStatusCurrencyCodUpdate(@RequestBody AccountDto account) {
        log.debug("Call method nameTypeStatusCurrencyCodUpdate with account {}", account);
        return accountConverter.toDto(accountService.update(accountConverter.toEntity(account)));
    }

    @PostMapping("/transfer/{iban1}/{iban2}/{amount}")
    public TransactionDto transferMoney(@PathVariable(name = "iban1") String debitAccount,
                                        @PathVariable(name = "iban2") String creditAccount,
                                        @PathVariable double amount) {
        log.debug("Call method transfer with first iban {} and second iban {}", debitAccount, creditAccount);
        return transactionConverter.toDto(transactionService.transfer(debitAccount, creditAccount, amount));
    }

    @DeleteMapping
    public void remove(@RequestBody AccountDto account) {
        log.debug("Call method remove with account {}", account);
        accountService.remove(accountService.getById(account.getId()));
    }
}