package org.telran.bankproject.com.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.AgreementDto;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.service.ClientService;

import java.sql.Timestamp;

@Component
public class AccountDtoConverter implements DtoConverter<Account, AccountDto> {

    @Autowired
    private ClientService clientService;

    @Override
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getId(), new ClientDto(account.getClient().getId(), null,
                null, account.getClient().getStatus(), account.getClient().getTaxCode(),
                account.getClient().getFirstName(), account.getClient().getLastName(), account.getClient().getEmail(),
                account.getClient().getAddress(), account.getClient().getPhone()), new AgreementDto(
                account.getAgreement().getId(), null, null, account.getAgreement().getInterestRate(),
                account.getAgreement().getStatus(), account.getAgreement().getSum()), account.getDebitTransactions()
                .stream().map(x -> new TransactionDto(x.getId(), null, null, x.getType(),
                        x.getAmount(), x.getDescription())).toList(), account.getCreditTransactions().stream()
                .map(x -> new TransactionDto(x.getId(), null, null, x.getType(),
                        x.getAmount(), x.getDescription())).toList(), account.getName(), account.getType(),
                account.getStatus(), account.getBalance(), account.getCurrencyCode());
    }

    @Override
    public Account toEntity(AccountDto account) {
        Client client = clientService.getAll().stream().filter(x -> x.getFirstName().equals(account.getClient()
                .getFirstName()) && x.getTaxCode().equals(account.getClient().getTaxCode())
                && x.getEmail().equals(account.getClient().getEmail())).findFirst().orElse(null);
        return new Account(account.getId(), client, null, null, null,
                account.getName(), account.getType(), account.getStatus(), account.getBalance(), account.getCurrencyCode(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}