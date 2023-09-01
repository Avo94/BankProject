package org.telran.bankproject.com.service.converter.currency;

import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.enums.CurrencyCode;

import java.math.BigDecimal;

public class CurrencyConverter {

    public static BigDecimal convert(Account debit, Account credit, BigDecimal amount) {
        if (debit.getCurrencyCode().equals(CurrencyCode.USD)) {
            if (credit.getCurrencyCode().equals(CurrencyCode.EUR)) amount = amount.multiply(BigDecimal.valueOf(0.93));
            if (credit.getCurrencyCode().equals(CurrencyCode.CHF)) amount = amount.multiply(BigDecimal.valueOf(0.88));
        }
        if (debit.getCurrencyCode().equals(CurrencyCode.EUR)) {
            if (credit.getCurrencyCode().equals(CurrencyCode.USD)) amount = amount.multiply(BigDecimal.valueOf(1.08));
            if (credit.getCurrencyCode().equals(CurrencyCode.CHF)) amount = amount.multiply(BigDecimal.valueOf(0.95));
        }
        if (debit.getCurrencyCode().equals(CurrencyCode.CHF)) {
            if (credit.getCurrencyCode().equals(CurrencyCode.USD)) amount = amount.multiply(BigDecimal.valueOf(1.13));
            if (credit.getCurrencyCode().equals(CurrencyCode.EUR)) amount = amount.multiply(BigDecimal.valueOf(1.05));
        }
        return amount;
    }
}