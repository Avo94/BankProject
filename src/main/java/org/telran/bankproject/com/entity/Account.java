package org.telran.bankproject.com.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client clientId;
    @OneToOne(mappedBy = "accountId")
    private Agreement agreement;
    @OneToOne(mappedBy = "debitAccountId")
    private Transaction debitTransaction;
    @OneToOne(mappedBy = "creditAccountId")
    private Transaction creditTransaction;
    private String name;
    private int type;
    private int status;
    private double balance;
    private int currencyCode;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Account() {
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

    public Transaction getDebitTransaction() {
        return debitTransaction;
    }

    public void setDebitTransaction(Transaction debitTransaction) {
        this.debitTransaction = debitTransaction;
    }

    public Transaction getCreditTransaction() {
        return creditTransaction;
    }

    public void setCreditTransaction(Transaction creditTransaction) {
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