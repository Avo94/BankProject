package org.telran.bankproject.com.dto;

public class AccountDto {

    private long id;
    private String name;
    private double balance;
    private int currencyCode;

    public AccountDto(long id, String name, double balance, int currencyCode) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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