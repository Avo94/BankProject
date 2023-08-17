package org.telran.bankproject.com.service.converter;

public interface DtoConverter<ENTITY, DTO> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);
}