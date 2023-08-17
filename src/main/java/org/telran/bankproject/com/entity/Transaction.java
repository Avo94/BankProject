package org.telran.bankproject.com.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debitAccountId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account creditAccountId;
    private int type;
    private double amount;
    private String description;
    private Timestamp createdAt;

    public Transaction() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(Account debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public Account getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(Account creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}