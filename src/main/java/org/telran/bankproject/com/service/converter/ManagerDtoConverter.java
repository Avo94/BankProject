package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ClientDto;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.dto.ProductDto;
import org.telran.bankproject.com.entity.Manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManagerDtoConverter implements DtoConverter<Manager, ManagerDto> {

    @Override
    public ManagerDto toDto(Manager manager) {


        return new ManagerDto(manager.getId(),
                manager.getClients().stream().map(x -> new ClientDto(x.getId(), null, null,
                        x.getStatus(), x.getTaxCode(), x.getFirstName(), x.getLastName(), x.getEmail(),
                        x.getAddress(), x.getPhone())).toList(), manager.getProducts()
                .stream().map(x -> new ProductDto(x.getId(), null, null, x.getName(),
                        x.getCurrencyCode(), x.getInterestRate(), x.getProductLimit())).toList(),
                manager.getFirstName(), manager.getLastName(), manager.getStatus());
    }

    @Override
    public Manager toEntity(ManagerDto manager) {
        return new Manager(manager.getId(), null, null,
                manager.getFirstName(), manager.getLastName(), manager.getStatus(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}