package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Transaction;

@Component
public class TransactionDtoConverter implements DtoConverter<Transaction, TransactionDto> {

    @Override
    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getDebitAccountId(),
                transaction.getCreditAccountId(), transaction.getType(), transaction.getAmount(),
                transaction.getDescription(), transaction.getCreatedAt());
    }

    @Override
    public Transaction toEntity(TransactionDto transactionDto) {
        throw new UnsupportedOperationException();
    }
}