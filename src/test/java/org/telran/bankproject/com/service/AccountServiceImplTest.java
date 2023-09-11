package org.telran.bankproject.com.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.repository.AccountRepository;
import org.telran.bankproject.com.service.converter.currency.CurrencyConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    @Mock
    private AccountRepository accountRepository;

    private final Transaction debitTransaction = new Transaction();
    private final Transaction creditTransaction = new Transaction();

    @BeforeEach
    public void init() {
        Account account = new Account();
        account.setIban("1234567890987654");
        account.setBalance(BigDecimal.valueOf(10000));
        account.setClient(new Client());
        account.getClient().setLogin("AAllova112");

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        account.setDebitTransactions(List.of(transaction1));
        account.setCreditTransactions(List.of(transaction2));
        debitTransaction.setId(1);
        transaction1.setId(1);
        creditTransaction.setId(1);
        transaction2.setId(1);

        Mockito
                .when(accountRepository.findByIban(account.getIban()))
                .thenReturn(Optional.of(account));
    }

    @Test
    void getByIban() {
        Account account = accountService.getByIban("1234567890987654");
        assertEquals("1234567890987654", account.getIban());
    }

    @Test
    void getTransactions() {
        List<Long> list1 = Stream.of(debitTransaction, creditTransaction).map(Transaction::getId).toList();
        List<Long> list2 = accountService.getTransactions("1234567890987654")
                .stream().map(Transaction::getId).toList();

        assertEquals(list1, list2);
    }

    @Test
    void getBalance() {
        double balance = accountService.getBalance("1234567890987654");
        assertEquals(10000, balance);
    }

    @Test
    void topUp() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "AAllova112",
                "$2a$10$.1n8bmOpgQfONOzjlEQG7uf0suE0wONxUlc5fgcQchqUU0s7Z20YC",
                List.of(new SimpleGrantedAuthority("user"))));

        double amount = accountService.topUp("1234567890987654", 1000);
        assertEquals(11000, amount);
    }
}