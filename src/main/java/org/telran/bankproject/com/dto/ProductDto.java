package org.telran.bankproject.com.dto;

public class ProductDto {

    private long id;
    private String name;
    private int status;
    private int currencyCode;
    private double interestRate;

    public ProductDto(long id, String name, int status, int currencyCode, double interestRate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.currencyCode = currencyCode;
        this.interestRate = interestRate;
    }

    public ProductDto() {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}