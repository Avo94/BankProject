package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ProductDto;
import org.telran.bankproject.com.entity.Product;

@Component
public class ProductDtoConverter implements DtoConverter<Product, ProductDto> {

    @Override
    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getStatus(), product.getCurrencyCode(), product.getInterestRate());
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        throw new UnsupportedOperationException();
    }
}
