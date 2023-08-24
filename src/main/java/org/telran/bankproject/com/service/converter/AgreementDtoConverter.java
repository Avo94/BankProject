package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AccountDto;
import org.telran.bankproject.com.dto.AgreementDto;
import org.telran.bankproject.com.dto.ProductDto;
import org.telran.bankproject.com.entity.Agreement;

@Component
public class AgreementDtoConverter implements DtoConverter<Agreement, AgreementDto> {

    @Override
    public AgreementDto toDto(Agreement agreement) {
        return new AgreementDto(agreement.getId(), new AccountDto(agreement.getAccount().getId(), null,
                null, null, null, agreement.getAccount().getName(),
                agreement.getAccount().getType(), agreement.getAccount().getStatus(), agreement.getAccount()
                .getBalance(), agreement.getAccount().getCurrencyCode()), new ProductDto(agreement.getProduct()
                .getId(), null, null, agreement.getProduct().getName(), agreement.getProduct()
                .getCurrencyCode(), agreement.getProduct().getInterestRate(), agreement.getProduct()
                .getProductLimit()), agreement.getInterestRate(), agreement.getStatus(), agreement.getSum());
    }

    @Override
    public Agreement toEntity(AgreementDto agreementDto) {
        throw new UnsupportedOperationException();
    }
}