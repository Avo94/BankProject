package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Transaction;

import java.util.List;

public class AccountDto {


    private long id;
    private Client clientId;
    private Agreement agreement;
    private List<Transaction> debitTransaction;
    private List<Transaction> creditTransaction;
    private String name;
    private int type;
    private int status;
    private double balance;
    private int currencyCode;

    public AccountDto(long id, Client clientId, Agreement agreement, List<Transaction> debitTransaction,
                      List<Transaction> creditTransaction, String name, int type, int status,
                      double balance, int currencyCode) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }
}