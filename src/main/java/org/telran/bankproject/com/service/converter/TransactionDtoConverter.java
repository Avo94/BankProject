package org.telran.bankproject.com.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.service.AccountService;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

@Component
public class TransactionDtoConverter implements DtoConverter<Transaction, TransactionDto> {

    @Autowired
    private AccountService accountService;

    @Override
    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(),
                new AccountDto(transaction.getDebitAccount().getId(),
                        null, null, null, null, transaction
                        .getDebitAccount().getName(), transaction.getDebitAccount().getIban(), transaction
                        .getDebitAccount().getType(), transaction.getDebitAccount().getStatus(), transaction
                        .getDebitAccount().getBalance(), transaction.getDebitAccount().getCurrencyCode()),
                new AccountDto(transaction.getCreditAccount().getId(), null, null, null,
                        null, transaction.getCreditAccount().getName(), transaction.getCreditAccount()
                        .getIban(), transaction.getCreditAccount().getType(), transaction.getCreditAccount().getStatus(),
                        transaction.getCreditAccount().getBalance(), transaction.getCreditAccount().getCurrencyCode()),
                transaction.getType(), transaction.getAmount(), transaction.getDescription());
    }

    @Override
    public Transaction toEntity(TransactionDto transactionDto) {
        if (transactionDto.getDebitAccount() == null)
            throw new EntityNotFoundException("The \"debitAccount\" field cannot be empty");
        if (transactionDto.getCreditAccount() == null)
            throw new EntityNotFoundException("The \"creditAccount\" field cannot be empty");

        Account debitAccount = accountService.getAll().stream().filter(x -> x.getId() ==
                (transactionDto.getDebitAccount().getId())).findFirst().orElse(null);
        Account credirAccount = accountService.getAll().stream().filter(x -> x.getId() ==
                (transactionDto.getCreditAccount().getId())).findFirst().orElse(null);

        if (debitAccount == null)
            throw new EntityNotFoundException("Such debitAccount was not found in the database");
        if (credirAccount == null)
            throw new EntityNotFoundException("Such creditAccount was not found in the database");

        return new Transaction(transactionDto.getId(), debitAccount, credirAccount, transactionDto.getType(),
                transactionDto.getAmount(), transactionDto.getDescription(), new Timestamp(System.currentTimeMillis()));
    }
}