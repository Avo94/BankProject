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
import org.telran.bankproject.com.exceptions.NotEnoughMoneyException;
import org.telran.bankproject.com.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionService transactionService = new TransactionServiceImpl();

    @Mock
    private AccountService accountService = new AccountServiceImpl();

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void init() {
        Account account = new Account();
        account.setIban("1234567890987654");
        account.setBalance(BigDecimal.valueOf(10000));
        account.setClient(new Client());
        account.getClient().setLogin("AAllova112");
        account.setCurrencyCode(CurrencyCode.USD);

        Account accountTwo = new Account();
        accountTwo.setIban("3456789098765432");
        accountTwo.setBalance(BigDecimal.valueOf(100000));
        accountTwo.setCurrencyCode(CurrencyCode.EUR);

        Transaction transaction = new Transaction();
        transaction.setDebitAccount(account);
        transaction.setCreditAccount(accountTwo);

        Mockito
                .when(accountService.getByIban(account.getIban()))
                .thenReturn(account);
        Mockito
                .when(accountService.getByIban(accountTwo.getIban()))
                .thenReturn(accountTwo);
        Mockito
                .lenient().when(transactionRepository.getReferenceById(1L))
                .thenReturn(transaction);
    }

    @Test
    void transfer() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "AAllova112",
                "$2a$10$.1n8bmOpgQfONOzjlEQG7uf0suE0wONxUlc5fgcQchqUU0s7Z20YC",
                List.of(new SimpleGrantedAuthority("user"))));

        transactionService.transfer("1234567890987654", "3456789098765432", 500);
        Transaction transaction = transactionRepository.getReferenceById(1L);

        assertEquals(9500.0, transaction.getDebitAccount().getBalance().doubleValue());
        assertEquals(100465.0, transaction.getCreditAccount().getBalance().doubleValue());
    }

    @Test
    void transferWithNoEnoughMoney() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "AAllova112",
                "$2a$10$.1n8bmOpgQfONOzjlEQG7uf0suE0wONxUlc5fgcQchqUU0s7Z20YC",
                List.of(new SimpleGrantedAuthority("user"))));

        assertThrows(NotEnoughMoneyException.class,
                () -> transactionService.transfer("1234567890987654", "3456789098765432", 500000));
    }
}