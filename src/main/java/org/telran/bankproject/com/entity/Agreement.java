package org.telran.bankproject.com.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.telran.bankproject.com.enums.Status;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonManagedReference
    private Account accountId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonManagedReference
    private Product productId;
    private double interestRate;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private double sum;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Agreement(long id, Account accountId, Product productId, double interestRate, Status status,
                     double sum, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.productId = productId;
        this.interestRate = interestRate;
        this.status = status;
        this.sum = sum;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Agreement() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
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