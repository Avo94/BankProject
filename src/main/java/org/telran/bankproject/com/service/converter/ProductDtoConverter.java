package org.telran.bankproject.com.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.AgreementDto;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.dto.ProductDto;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.service.ManagerService;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

@Component
public class ProductDtoConverter implements DtoConverter<Product, ProductDto> {

    @Autowired
    private ManagerService managerService;

    @Override
    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), new ManagerDto(product.getManager().getId(), null, null,
                product.getManager().getFirstName(), product.getManager().getLastName(), product.getManager()
                .getStatus()), new AgreementDto(product.getAgreement().getId(), null, null,
                product.getAgreement().getInterestRate(), product.getAgreement().getStatus(),
                product.getAgreement().getSum()), product.getName(), product.getCurrencyCode(),
                product.getInterestRate(), product.getProductLimit());
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        if (productDto.getManager() == null) throw new EntityNotFoundException("The \"manager\" field cannot be empty");
        Manager manager = managerService.getAll().stream().filter(x -> x.getFirstName()
                .equals(productDto.getManager().getFirstName()) && x.getLastName()
                .equals(productDto.getManager().getLastName())).findFirst().orElse(null);
        if (manager == null) throw new EntityNotFoundException("Such manager was not found in the database");
        return new Product(productDto.getId(), manager, null, productDto.getName(), Status.ACTIVE,
                productDto.getCurrencyCode(), productDto.getInterestRate(), productDto.getProductLimit(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}