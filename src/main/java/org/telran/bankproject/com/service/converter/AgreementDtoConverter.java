package org.telran.bankproject.com.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.AgreementDto;
import org.telran.bankproject.com.dto.ProductDto;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.service.AccountService;
import org.telran.bankproject.com.service.ProductService;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

@Component
public class AgreementDtoConverter implements DtoConverter<Agreement, AgreementDto> {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Override
    public AgreementDto toDto(Agreement agreement) {
        return new AgreementDto(agreement.getId(),
                new AccountDto(agreement.getAccount().getId(), null, null, null,
                        null, agreement.getAccount().getName(), agreement.getAccount().getIban(),
                        agreement.getAccount().getType(), agreement.getAccount().getStatus(),
                        agreement.getAccount().getBalance(), agreement.getAccount().getCurrencyCode()),
                new ProductDto(agreement.getProduct().getId(), null, null,
                        agreement.getProduct().getName(), agreement.getProduct().getCurrencyCode(),
                        agreement.getProduct().getInterestRate(), agreement.getProduct().getProductLimit()),
                agreement.getInterestRate(), agreement.getStatus(), agreement.getSum());
    }

    @Override
    public Agreement toEntity(AgreementDto agreementDto) {
        if (agreementDto.getAccount() == null)
            throw new EntityNotFoundException("The \"account\" field cannot be empty");
        if (agreementDto.getProduct() == null)
            throw new EntityNotFoundException("The \"account\" field cannot be empty");

        Account account = accountService.getAll().stream().filter(x -> x.getClient()
                .equals(agreementDto.getAccount().getClient())).findFirst().orElse(null);
        Product product = productService.getAll().stream().filter(x -> x.getManager().getClients()
                .equals(agreementDto.getAccount().getClient())).findFirst().orElse(null);

        if (account == null) throw new EntityNotFoundException("Such account was not found in the database");
        if (product == null) throw new EntityNotFoundException("Such product was not found in the database");

        return new Agreement(agreementDto.getId(), account, product, agreementDto.getInterestRate(),
                agreementDto.getStatus(), agreementDto.getSum(), new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));
    }
}