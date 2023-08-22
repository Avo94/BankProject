package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.enums.Type;

import java.sql.Timestamp;

public class TransactionDto {

    private long id;
    private Account debitAccountId;
    private Account creditAccountId;
    private Type type;
    private double amount;
    private String description;
    private Timestamp createdAt;

    public TransactionDto(long id, Account debitAccountId, Account creditAccountId, Type type,
                          double amount, String description, Timestamp createdAt) {
        this.id = id;
        this.debitAccountId = debitAccountId;
        this.creditAccountId = creditAccountId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public TransactionDto() {
        //
    }

    public long getId() {
        return id;
    }

    public Account getDebitAccountId() {
        return debitAccountId;
    }

    public Account getCreditAccountId() {
        return creditAccountId;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}