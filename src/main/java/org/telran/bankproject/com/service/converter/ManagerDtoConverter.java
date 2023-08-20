package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Manager;

import java.sql.Timestamp;

@Component
public class ManagerDtoConverter implements DtoConverter<Manager, ManagerDto> {

    @Override
    public ManagerDto toDto(Manager manager) {
        return new ManagerDto(manager.getId(), manager.getClients(), manager.getProduct(),
                manager.getFirstName(), manager.getLastName(), manager.getStatus());
    }

    @Override
    public Manager toEntity(ManagerDto manager) {
        return new Manager(manager.getId(), manager.getClients(), manager.getProduct(),
                manager.getFirstName(), manager.getLastName(), manager.getStatus(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}