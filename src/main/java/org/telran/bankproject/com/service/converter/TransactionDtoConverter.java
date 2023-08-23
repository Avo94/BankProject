package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Transaction;

@Component
public class TransactionDtoConverter implements DtoConverter<Transaction, TransactionDto> {

    @Override
    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), new AccountDto(transaction.getDebitAccountId().getId(),
                null, null, null, null, transaction.getDebitAccountId()
                .getName(), transaction.getDebitAccountId().getType(), transaction.getDebitAccountId().getStatus(),
                transaction.getDebitAccountId().getBalance(), transaction.getDebitAccountId().getCurrencyCode()),
                new AccountDto(transaction.getCreditAccountId().getId(), null, null, null,
                        null, transaction.getCreditAccountId().getName(), transaction
                        .getCreditAccountId().getType(), transaction.getCreditAccountId().getStatus(), transaction
                        .getCreditAccountId().getBalance(), transaction.getCreditAccountId().getCurrencyCode()),
                transaction.getType(), transaction.getAmount(), transaction.getDescription());
    }

    @Override
    public Transaction toEntity(TransactionDto transactionDto) {
        throw new UnsupportedOperationException();
    }
}