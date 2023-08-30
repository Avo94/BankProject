package org.telran.bankproject.com.entity;

import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;
    @OneToOne(mappedBy = "product")
    private Agreement agreement;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Enumerated(value = EnumType.STRING)
    private CurrencyCode currencyCode;
    private double interestRate;
    private int productLimit;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Product(long id, Manager manager, Agreement agreement, String name, Status status,
                   CurrencyCode currencyCode, double interestRate, int productLimit,
                   Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.manager = manager;
        this.agreement = agreement;
        this.name = name;
        this.status = status;
        this.currencyCode = currencyCode;
        this.interestRate = interestRate;
        this.productLimit = productLimit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(int productLimit) {
        this.productLimit = productLimit;
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