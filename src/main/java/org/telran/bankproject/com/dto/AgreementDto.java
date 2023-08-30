package org.telran.bankproject.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.telran.bankproject.com.enums.Status;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgreementDto {

    private long id;
    private AccountDto account;
    private ProductDto product;
    private double interestRate;
    private Status status;
    private double sum;

    public AgreementDto(long id, AccountDto account, ProductDto product, double interestRate,
                        Status status, double sum) {
        this.id = id;
        this.account = account;
        this.product = product;
        this.interestRate = interestRate;
        this.status = status;
        this.sum = sum;
    }

    public AgreementDto() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
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
}