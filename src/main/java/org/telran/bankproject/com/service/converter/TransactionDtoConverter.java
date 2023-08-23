package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Transaction;

@Component
public class TransactionDtoConverter implements DtoConverter<Transaction, TransactionDto> {

    @Override
    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), new AccountDto(transaction.getDebitAccount().getId(),
                null, null, null, null, transaction.getDebitAccount()
                .getName(), transaction.getDebitAccount().getType(), transaction.getDebitAccount().getStatus(),
                transaction.getDebitAccount().getBalance(), transaction.getDebitAccount().getCurrencyCode()),
                new AccountDto(transaction.getCreditAccount().getId(), null, null, null,
                        null, transaction.getCreditAccount().getName(), transaction
                        .getCreditAccount().getType(), transaction.getCreditAccount().getStatus(), transaction
                        .getCreditAccount().getBalance(), transaction.getCreditAccount().getCurrencyCode()),
                transaction.getType(), transaction.getAmount(), transaction.getDescription());
    }

    @Override
    public Transaction toEntity(TransactionDto transactionDto) {
        throw new UnsupportedOperationException();
    }
}