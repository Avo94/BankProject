package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Client;

public class AccountDto {

    private Client clientId;
    private Agreement agreement;
    private String name;
    private int type;
    private double balance;
    private int currencyCode;

    public AccountDto(Client clientId, Agreement agreement, String name, int type, double balance, int currencyCode) {
        this.clientId = clientId;
        this.agreement = agreement;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.currencyCode = currencyCode;
    }

    public AccountDto() {
        //
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