package org.telran.bankproject.com.entity;

import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @OneToOne(mappedBy = "account")
    private Agreement agreement;
    @OneToMany(mappedBy = "debitAccount")
    private List<Transaction> debitTransactions;
    @OneToMany(mappedBy = "creditAccount")
    private List<Transaction> creditTransactions;
    private String name;
    private String iban;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private double balance;
    @Enumerated(value = EnumType.STRING)
    private CurrencyCode currencyCode;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Account(long id, Client client, Agreement agreement, List<Transaction> debitTransactions,
                   List<Transaction> creditTransactions, String name, String iban, Type type, Status status,
                   double balance, CurrencyCode currencyCode, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.client = client;
        this.agreement = agreement;
        this.debitTransactions = debitTransactions;
        this.creditTransactions = creditTransactions;
        this.name = name;
        this.iban = iban;
        this.type = type;
        this.status = status;
        this.balance = balance;
        this.currencyCode = currencyCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Account() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public List<Transaction> getDebitTransactions() {
        return debitTransactions;
    }

    public void setDebitTransactions(List<Transaction> debitTransactions) {
        this.debitTransactions = debitTransactions;
    }

    public List<Transaction> getCreditTransactions() {
        return creditTransactions;
    }

    public void setCreditTransactions(List<Transaction> creditTransactions) {
        this.creditTransactions = creditTransactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}