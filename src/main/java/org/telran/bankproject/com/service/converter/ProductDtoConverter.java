package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ProductDto;
import org.telran.bankproject.com.entity.Product;

@Component
public class ProductDtoConverter implements DtoConverter<Product, ProductDto> {

    @Override
    public ProductDto toDto(Product product) {
        return new ProductDto(product.getManagerId(), product.getAgreement(), product.getName(),
                product.getCurrencyCode(), product.getInterestRate(), product.getProductLimit());
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        throw new UnsupportedOperationException();
    }
}