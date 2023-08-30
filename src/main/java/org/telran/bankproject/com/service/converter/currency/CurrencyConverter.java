package org.telran.bankproject.com.service.converter.currency;

import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.enums.CurrencyCode;

public class CurrencyConverter {

    public static double convert(Account debit, Account credit, double amount) {
        if (debit.getCurrencyCode().equals(CurrencyCode.USD)) {
            if (credit.getCurrencyCode().equals(CurrencyCode.EUR)) amount *= 0.93;
            if (credit.getCurrencyCode().equals(CurrencyCode.CHF)) amount *= 0.88;
        }
        if (debit.getCurrencyCode().equals(CurrencyCode.EUR)) {
            if (credit.getCurrencyCode().equals(CurrencyCode.USD)) amount *= 1.08;
            if (credit.getCurrencyCode().equals(CurrencyCode.CHF)) amount *= 0.95;
        }
        if (debit.getCurrencyCode().equals(CurrencyCode.CHF)) {
            if (credit.getCurrencyCode().equals(CurrencyCode.USD)) amount *= 1.13;
            if (credit.getCurrencyCode().equals(CurrencyCode.EUR)) amount *= 1.05;
        }
        return amount;
    }
}