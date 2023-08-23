package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;

import java.util.List;

public class AccountDto {

    private long id;
    private ClientDto client;
    private AgreementDto agreement;
    private List<TransactionDto> debitTransactions;
    private List<TransactionDto> creditTransactions;
    private String name;
    private Type type;
    private Status status;
    private double balance;
    private CurrencyCode currencyCode;

    public AccountDto(long id, ClientDto client, AgreementDto agreement, List<TransactionDto> debitTransactions,
                      List<TransactionDto> creditTransactions, String name, Type type, Status status,
                      double balance, CurrencyCode currencyCode) {
        this.id = id;
        this.client = client;
        this.agreement = agreement;
        this.debitTransactions = debitTransactions;
        this.creditTransactions = creditTransactions;
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

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public AgreementDto getAgreement() {
        return agreement;
    }

    public void setAgreement(AgreementDto agreement) {
        this.agreement = agreement;
    }

    public List<TransactionDto> getDebitTransactions() {
        return debitTransactions;
    }

    public void setDebitTransactions(List<TransactionDto> debitTransactions) {
        this.debitTransactions = debitTransactions;
    }

    public List<TransactionDto> getCreditTransactions() {
        return creditTransactions;
    }

    public void setCreditTransactions(List<TransactionDto> creditTransactions) {
        this.creditTransactions = creditTransactions;
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