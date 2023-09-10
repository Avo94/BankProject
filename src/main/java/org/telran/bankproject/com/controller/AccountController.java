package org.telran.bankproject.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Account Controller", description = "Allows you to carry out operations on client accounts")
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

    @Operation(summary = "List of accounts",
            description = "Allows you to get a list of all accounts in the system")
    @SecurityRequirement(name = "basicauth")
    @GetMapping
    public List<AccountDto> getAll() {
        log.debug("Call method getAll");
        return accountService.getAll().stream()
                .map(account -> accountConverter.toDto(account)).collect(Collectors.toList());
    }

    @Operation(summary = "Find account by ID",
            description = "Allows you to find an account in the system by its ID")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable(name = "id") long id) {
        log.debug("Call method getById with id {}", id);
        return accountConverter.toDto(accountService.getById(id));
    }

    @Operation(summary = "Account transaction history",
            description = "Allows you to find the transaction history of an account by its IBAN")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/transactions/{iban}")
    public List<TransactionDto> getTransactionHistory(@PathVariable String iban) {
        log.debug("Call method getTransactionHistory with iban {}", iban);
        return accountService.getTransactions(iban).stream()
                .map(x -> transactionConverter.toDto(x)).toList();
    }

    @Operation(summary = "Get account balance",
            description = "Allows you to get the current account balance by its IBAN")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/balance/{iban}")
    public double getBalance(@PathVariable String iban) {
        log.debug("Call method getBalance with iban {}", iban);
        return accountService.getBalance(iban);
    }

    @Operation(summary = "Add account",
            description = "Allows you to add a new account for an existing client in the system")
    @SecurityRequirement(name = "basicauth")
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

    @Operation(summary = "Top up your account",
            description = "Allows you to top up your account using IBAN")
    @SecurityRequirement(name = "basicauth")
    @PostMapping("topup/{iban}/{amount}")
    public double topUpAccount(@PathVariable String iban, @PathVariable double amount) {
        log.debug("Call method topUpAccount with iban {}, and {}", iban, amount);
        return accountService.topUp(iban, amount);
    }

    @Operation(summary = "Update account",
            description = "Allows you to update account information: name, type, status, currency code.")
    @SecurityRequirement(name = "basicauth")
    @PostMapping("/update")
    public AccountDto nameTypeStatusCurrencyCodUpdate(@RequestBody AccountDto account) {
        log.debug("Call method nameTypeStatusCurrencyCodUpdate with account {}", account);
        return accountConverter.toDto(accountService.update(accountConverter.toEntity(account)));
    }

    @Operation(summary = "Money transfer",
            description = "Allows you to transfer money from your account to any other account using IBAN")
    @SecurityRequirement(name = "basicauth")
    @PostMapping("/transfer/{iban1}/{iban2}/{amount}")
    public TransactionDto transferMoney(@PathVariable(name = "iban1") String debitAccount,
                                        @PathVariable(name = "iban2") String creditAccount,
                                        @PathVariable double amount) {
        log.debug("Call method transfer with first iban {} and second iban {}", debitAccount, creditAccount);
        return transactionConverter.toDto(transactionService.transfer(debitAccount, creditAccount, amount));
    }

    @Operation(summary = "Delete account",
            description = "Allows you to delete an existing account from the system")
    @SecurityRequirement(name = "basicauth")
    @DeleteMapping
    public void remove(@RequestBody AccountDto account) {
        log.debug("Call method remove with account {}", account);
        accountService.remove(accountService.getById(account.getId()));
    }
}