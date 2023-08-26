package org.telran.bankproject.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telran.bankproject.com.dto.AgreementDto;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.service.AgreementService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("agreements")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private DtoConverter<Agreement, AgreementDto> converter;

    @GetMapping
    public List<AgreementDto> getAll() {
        return agreementService.getAll().stream().map(x-> converter.toDto(x)).collect(Collectors.toList());
    }

    @PostMapping
    public AgreementDto create(AgreementDto agreementDto) {
        return converter.toDto(agreementService.add(converter.toEntity(agreementDto)));
    }
}