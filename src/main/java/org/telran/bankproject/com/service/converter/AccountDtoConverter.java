package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.entity.Account;

@Component
public class AccountDtoConverter implements DtoConverter<Account, AccountDto> {

    @Override
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getClientId(), account.getAgreement(), account.getName(), account.getType(), account.getBalance(), account.getCurrencyCode());
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        throw new UnsupportedOperationException();
    }
}
