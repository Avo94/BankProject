package org.telran.bankproject.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.service.ManagerService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;

@RestController
@RequestMapping("managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private DtoConverter<Manager, ManagerDto> managerConverter;

    @GetMapping
    public List<ManagerDto> getAll() {
        return managerService.getAll().stream().map(manager -> managerConverter.toDto(manager)).toList();
    }

    @GetMapping("/{id}")
    public ManagerDto getById(@PathVariable(name = "id") long id) {
        return managerConverter.toDto(managerService.getById(id));
    }

    @PostMapping
    public ManagerDto addManager(@RequestBody ManagerDto manager) {
        return managerConverter.toDto(managerService.add(managerConverter.toEntity(manager)));
    }

    @DeleteMapping
    public void remove(@RequestBody ManagerDto manager) {
        managerService.remove(managerService.getById(manager.getId()));
    }
}