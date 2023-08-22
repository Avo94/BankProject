package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.enums.Type;

public class AgreementDto {

    private Account accountId;
    private Product productId;
    private Type type;
    private double amount;
    private String description;

    public AgreementDto(Account accountId, Product productId, Type type, double amount, String description) {
        this.accountId = accountId;
        this.productId = productId;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public AgreementDto() {
        //
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
}