package org.telran.bankproject.com.service.converter;

import org.springframework.stereotype.Component;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Manager;

@Component
public class ManagerDtoConverter implements DtoConverter<Manager, ManagerDto> {

    @Override
    public ManagerDto toDto(Manager manager) {
        return new ManagerDto(manager.getClients(),manager.getProduct(), manager.getFirstName(), manager.getLastName());
    }

    @Override
    public Manager toEntity(ManagerDto managerDto) {
        throw new UnsupportedOperationException();
    }
}
