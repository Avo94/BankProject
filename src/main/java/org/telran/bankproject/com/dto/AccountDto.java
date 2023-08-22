package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;

import java.util.List;

public class AccountDto {

    private long id;
    private Client clientId;
    private Agreement agreement;
    private List<Transaction> debitTransaction;
    private List<Transaction> creditTransaction;
    private String name;
    private Type type;
    private Status status;
    private double balance;
    private CurrencyCode currencyCode;

    public AccountDto(long id, Client clientId, Agreement agreement, List<Transaction> debitTransaction,
                      List<Transaction> creditTransaction, String name, Type type, Status status,
                      double balance, CurrencyCode currencyCode) {
        this.id = id;
        this.clientId = clientId;
        this.agreement = agreement;
        this.debitTransaction = debitTransaction;
        this.creditTransaction = creditTransaction;
        this.name = name;
        this.type = type;
        this.status = status;
        this.balance = balance;
        this.currencyCode = currencyCode;
    }

    public AccountDto() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public List<Transaction> getDebitTransaction() {
        return debitTransaction;
    }

    public void setDebitTransaction(List<Transaction> debitTransaction) {
        this.debitTransaction = debitTransaction;
    }

    public List<Transaction> getCreditTransaction() {
        return creditTransaction;
    }

    public void setCreditTransaction(List<Transaction> creditTransaction) {
        this.creditTransaction = creditTransaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}