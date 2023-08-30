package org.telran.bankproject.com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ManagerService managerService;
    @Autowired
    private DtoConverter<Manager, ManagerDto> managerConverter;

    @GetMapping
    public List<ManagerDto> getAll() {
        log.debug("Call method getAll");
        return managerService.getAll().stream().map(manager -> managerConverter.toDto(manager)).toList();
    }

    @GetMapping("/{id}")
    public ManagerDto getById(@PathVariable(name = "id") long id) {
        log.debug("Call method getById with id {}", id);
        return managerConverter.toDto(managerService.getById(id));
    }

    @PostMapping
    public ManagerDto addManager(@RequestBody ManagerDto manager) {
        log.debug("Call method addManager with manager {}", manager);
        return managerConverter.toDto(managerService.add(managerConverter.toEntity(manager)));
    }

    @DeleteMapping
    public void remove(@RequestBody ManagerDto manager) {
        log.debug("Call method remove with manager {}", manager);
        managerService.remove(managerService.getById(manager.getId()));
    }
}