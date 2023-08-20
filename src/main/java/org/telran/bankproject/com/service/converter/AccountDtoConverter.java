package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.entity.Account;

import java.sql.Timestamp;

@Component
public class AccountDtoConverter implements DtoConverter<Account, AccountDto> {

    @Override
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getId(), account.getClientId(), account.getAgreement(),
                account.getDebitTransaction(), account.getCreditTransaction(), account.getName(),
                account.getType(), account.getStatus(), account.getBalance(), account.getCurrencyCode());
    }

    @Override
    public Account toEntity(AccountDto account) {
      return new Account(account.getId(), account.getClientId(), account.getAgreement(),
              account.getDebitTransaction(), account.getCreditTransaction(), account.getName(),
              account.getType(), account.getStatus(), account.getBalance(), account.getCurrencyCode(),
              new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}