package org.telran.bankproject.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.service.AccountService;
import org.telran.bankproject.com.service.AgreementService;
import org.telran.bankproject.com.service.TransactionService;
import org.telran.bankproject.com.service.converter.DtoConverter;
import org.telran.bankproject.com.service.security.ClientDetailService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;
    @MockBean
    private DtoConverter<Account, AccountDto> accountConverter;
    @MockBean
    private DtoConverter<Transaction, TransactionDto> transactionConverter;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AgreementService agreementService;
    @MockBean
    private ClientDetailService clientDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTransactionHistory() throws Exception {
        Transaction transaction = new Transaction(1, null, null,
                Type.SUCCESSFUL, 1000, "Successful", null);
        TransactionDto transactionDto = new TransactionDto(transaction.getId(), null, null,
                Type.SUCCESSFUL, transaction.getAmount(), transaction.getDescription());

        Mockito
                .when(accountService.getTransactions("1234567890987654"))
                .thenReturn(List.of(transaction));
        Mockito
                .when(transactionConverter.toDto(transaction))
                .thenReturn(transactionDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(transactionDto))));
    }

    @Test
    void getBalance() throws Exception {
        Mockito
                .when(accountService.getBalance("1234567890987654"))
                .thenReturn(1000.0);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(1000.0))));
    }

    @Test
    void topUpAccount() throws Exception {
        Mockito
                .when(accountService.topUp("1234567890987654", 1000))
                .thenReturn(10000.0);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(10000.0))));
    }

    @Test
    void nameTypeStatusCurrencyCodUpdate() throws Exception {
        Account account = new Account(1, null, null, null, null,
                "name", "iban", Type.STANDARD, Status.ACTIVE, BigDecimal.valueOf(9000.0),
                CurrencyCode.USD, null, null);
        AccountDto accountDto = new AccountDto(account.getId(), null, null, null,
                null, account.getName(), account.getIban(), account.getType(), account.getStatus(),
                account.getBalance(), account.getCurrencyCode());

        Mockito
                .when(accountService.update(account))
                .thenReturn(account);
        Mockito
                .when(accountConverter.toDto(account))
                .thenReturn(accountDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(accountDto))));
    }

    @Test
    void transferMoney() throws Exception {
        Transaction transaction = new Transaction(1, null, null,
                Type.SUCCESSFUL, 1000, "Successful", null);
        TransactionDto transactionDto = new TransactionDto(transaction.getId(), null, null,
                Type.SUCCESSFUL, transaction.getAmount(), transaction.getDescription());

        Mockito
                .when(transactionService.transfer("1234567890987654", "2345678909876543", 1000))
                .thenReturn(transaction);
        Mockito
                .when(transactionConverter.toDto(transaction))
                .thenReturn(transactionDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(transaction))));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}