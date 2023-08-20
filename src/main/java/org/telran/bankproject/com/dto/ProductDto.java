package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Manager;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private List<Manager> managerId = new ArrayList<>();
    private Agreement agreement;
    private String name;
    private int currencyCode;
    private double interestRate;
    private int productLimit;

    public ProductDto(List<Manager> managerId, Agreement agreement, String name, int currencyCode,
                      double interestRate, int productLimit) {
        this.managerId = managerId;
        this.agreement = agreement;
        this.name = name;
        this.currencyCode = currencyCode;
        this.interestRate = interestRate;
        this.productLimit = productLimit;
    }

    public ProductDto() {
        //
    }

    public List<Manager> getManagerId() {
        return managerId;
    }

    public void setManagerId(List<Manager> managerId) {
        this.managerId = managerId;
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

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
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
}