package org.telran.bankproject.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.AgreementDto;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.TransactionDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Transaction;
import org.telran.bankproject.com.enums.CurrencyCode;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.enums.Type;
import org.telran.bankproject.com.exceptions.NotEnoughMoneyException;
import org.telran.bankproject.com.repository.AccountRepository;
import org.telran.bankproject.com.service.AccountService;
import org.telran.bankproject.com.service.AgreementService;
import org.telran.bankproject.com.service.TransactionService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private DtoConverter<Account, AccountDto> accountConverter;
    @MockBean
    private DtoConverter<Transaction, TransactionDto> transactionConverter;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AgreementService agreementService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTransactionHistory() throws Exception {
        Transaction transaction = new Transaction(1, null, null,
                Type.SUCCESSFUL, 1000, "Successful", null);
        TransactionDto transactionDto = new TransactionDto(transaction.getId(), null, null,
                Type.SUCCESSFUL, transaction.getAmount(), transaction.getDescription());

        Mockito.when(accountService.getTransactions("1234567890987654"))
                .thenReturn(List.of(transaction));
        Mockito.when(transactionConverter.toDto(transaction))
                .thenReturn(transactionDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/transactions/{iban}",
                        "1234567890987654").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(transactionDto))));
    }

    @Test
    void getBalance() throws Exception {
        Mockito.when(accountService.getBalance("1234567890987654"))
                .thenReturn(1000.0);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance/{iban}",
                        "1234567890987654").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1000.0"));
    }

    @Test
    void topUpAccount() throws Exception {
        Mockito.when(accountService.topUp("1234567890987654", 1000))
                .thenReturn(10000.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/topup/{iban}/{amount}"
                        , "1234567890987654", 1000).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("10000.0"));
    }

    @Test
    void nameTypeStatusCurrencyCodUpdate() throws Exception {
        AccountDto inputAccount = new AccountDto(1, new ClientDto(), new AgreementDto(),
                null, null, "Standard account", "1234567890987654",
                Type.STANDARD, Status.ACTIVE, BigDecimal.valueOf(9000.0), CurrencyCode.USD);

        Account account = new Account(inputAccount.getId(), new Client(), new Agreement(),
                List.of(new Transaction()), List.of(new Transaction()),
                inputAccount.getName(), inputAccount.getIban(), inputAccount.getType(),
                inputAccount.getStatus(), inputAccount.getBalance(),
                inputAccount.getCurrencyCode(), null, null);

        AccountDto outputAccount = new AccountDto(account.getId(), inputAccount.getClient(),
                inputAccount.getAgreement(), inputAccount.getDebitTransactions(),
                inputAccount.getCreditTransactions(), account.getName(), account.getIban(), account.getType(),
                account.getStatus(), account.getBalance(), account.getCurrencyCode());

        Mockito.when(accountConverter.toEntity(Mockito.any(AccountDto.class))).thenReturn(account);
        Mockito.when(accountService.update(account)).thenReturn(account);
        Mockito.when(accountConverter.toDto(account)).thenReturn(outputAccount);

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/update")
                        .content(asJsonString(inputAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(outputAccount)));
    }

    @Test
    void transferMoney() throws Exception {
        Transaction transaction = new Transaction(1, null, null,
                Type.SUCCESSFUL, 1000, "Successful", null);
        TransactionDto transactionDto = new TransactionDto(transaction.getId(), new AccountDto(), new AccountDto(),
                Type.SUCCESSFUL, transaction.getAmount(), transaction.getDescription());

        Mockito.when(transactionService.transfer("1234567890987654", "2345678909876543", 1000))
                .thenReturn(transaction);
        Mockito.when(transactionConverter.toDto(transaction)).thenReturn(transactionDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/transfer/{iban1}/{iban2}/{amount}",
                                "1234567890987654", "2345678909876543", 1000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(transactionDto)));
    }

    @Test
    void transferWithNotEnoughMoney() throws Exception {
        Transaction transaction = new Transaction(1, new Account(), null,
                Type.FAILED, 10000, "Failed", null);
        transaction.getDebitAccount().setBalance(BigDecimal.valueOf(0));
        TransactionDto transactionDto = new TransactionDto(transaction.getId(), new AccountDto(), new AccountDto(),
                Type.FAILED, transaction.getAmount(), transaction.getDescription());

        Mockito.when(transactionService.transfer("1234567890987654", "2345678909876543", 10000))
                .thenReturn(transaction);
        Mockito.when(transactionConverter.toDto(transaction)).thenReturn(transactionDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/transfer/{iban1}/{iban2}/{amount}",
                                "1234567890987654", "2345678909876543", 10000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotEnoughMoneyException));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}