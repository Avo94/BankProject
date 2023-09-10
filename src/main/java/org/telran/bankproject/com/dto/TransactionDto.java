package org.telran.bankproject.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.telran.bankproject.com.enums.Type;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {

    private long id;
    private AccountDto debitAccount;
    private AccountDto creditAccount;
    @Schema(description = "SUCCESSFUL or FAILED")
    private Type type;
    private double amount;
    private String description;

    public TransactionDto(long id, AccountDto debitAccount, AccountDto creditAccount, Type type,
                          double amount, String description) {
        this.id = id;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public TransactionDto() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountDto getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(AccountDto debitAccount) {
        this.debitAccount = debitAccount;
    }

    public AccountDto getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(AccountDto creditAccount) {
        this.creditAccount = creditAccount;
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