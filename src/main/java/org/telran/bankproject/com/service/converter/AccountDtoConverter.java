package org.telran.bankproject.com.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.AgreementDto;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.service.ClientService;
import org.telran.bankproject.com.service.generator.IbanGenerator;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class AccountDtoConverter implements DtoConverter<Account, AccountDto> {

    @Autowired
    private ClientService clientService;

    @Autowired
    private IbanGenerator generator;

    @Override
    public AccountDto toDto(Account account) {
        List<TransactionDto> debitTransactions;
        List<TransactionDto> creditTransactions;

        if (account.getDebitTransactions() == null) debitTransactions = null;
        else debitTransactions = account.getDebitTransactions().stream().map(x -> new TransactionDto(x.getId(),
                null, null, x.getType(), x.getAmount(), x.getDescription())).toList();

        if (account.getCreditTransactions() == null) creditTransactions = null;
        else creditTransactions = account.getCreditTransactions().stream().map(x -> new TransactionDto(x.getId(),
                null, null, x.getType(), x.getAmount(), x.getDescription())).toList();

        return new AccountDto(account.getId(),
                new ClientDto(account.getClient().getId(), null, null, account.getClient()
                        .getStatus(), account.getClient().getTaxCode(), account.getClient().getFirstName(), account
                        .getClient().getLastName(), account.getClient().getLogin(), null, account.getClient()
                        .getEmail(), account.getClient().getAddress(), account.getClient().getPhone()),
                new AgreementDto(account.getAgreement().getId(), null, null, account.getAgreement()
                        .getInterestRate(), account.getAgreement().getStatus(), account.getAgreement().getSum()),
                debitTransactions, creditTransactions, account.getName(), account.getIban(), account.getType(),
                account.getStatus(), account.getBalance(), account.getCurrencyCode());
    }

    @Override
    public Account toEntity(AccountDto account) {
        if (account.getIban() != null) return new Account(account.getId(), null, null,
                null, null, account.getName(), account.getIban(), account.getType(),
                account.getStatus(), account.getBalance(), account.getCurrencyCode(), null, null);

        if (account.getClient() == null) throw new EntityNotFoundException("The \"client\" field cannot be empty");
        Client client = clientService.getAll().stream().filter(x -> x.getFirstName().equals(account.getClient()
                .getFirstName()) && x.getTaxCode().equals(account.getClient().getTaxCode())
                && x.getEmail().equals(account.getClient().getEmail())).findFirst().orElseThrow(() ->
                new EntityNotFoundException("Such manager was not found in the database"));

        return new Account(account.getId(), client, null, null,
                null, account.getName(), generator.generate(), account.getType(),
                account.getStatus(), account.getBalance(), account.getCurrencyCode(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}