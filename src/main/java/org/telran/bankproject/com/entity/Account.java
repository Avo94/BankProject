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
    @OneToMany(mappedBy = "debitAccountId")
    private List<Transaction> debitTransaction;
    @OneToMany(mappedBy = "creditAccountId")
    private List<Transaction> creditTransaction;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private double balance;
    @Enumerated(value = EnumType.STRING)
    private CurrencyCode currencyCode;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Account(long id, Client client, Agreement agreement, List<Transaction> debitTransaction,
                   List<Transaction> creditTransaction, String name, Type type, Status status,
                   double balance, CurrencyCode currencyCode, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.client = client;
        this.agreement = agreement;
        this.debitTransaction = debitTransaction;
        this.creditTransaction = creditTransaction;
        this.name = name;
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