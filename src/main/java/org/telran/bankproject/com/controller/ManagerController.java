package org.telran.bankproject.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.service.ManagerService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private DtoConverter<Manager, ManagerDto> managerConverter;

    @GetMapping
    public List<ManagerDto> getAll() {
        return managerService.getAll().stream().map(manager -> managerConverter.toDto(manager)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ManagerDto getById(@PathVariable(name = "id") long id) {
        return managerConverter.toDto(managerService.getById(id));
    }

    @PostMapping
    public Manager addClient(@RequestBody ManagerDto manager) {
        return managerService.add(managerConverter.toEntity(manager));
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable(name = "id") long id) {
        managerService.remove(id);
    }
}