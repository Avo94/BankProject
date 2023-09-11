package org.telran.bankproject.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telran.bankproject.com.dto.ManagerDto;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.service.ManagerService;
import org.telran.bankproject.com.service.converter.DtoConverter;

import java.util.List;

@Tag(name = "Manager Controller", description = "CRUD operations on bank managers")
@RestController
@RequestMapping("managers")
public class ManagerController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ManagerService managerService;
    @Autowired
    private DtoConverter<Manager, ManagerDto> managerConverter;

    @Operation(summary = "List of managers",
            description = "Allows you to get a list of all managers in the system")
    @SecurityRequirement(name = "basicauth")
    @GetMapping
    public List<ManagerDto> getAll() {
        log.debug("Call method getAll");
        return managerService.getAll().stream().map(manager -> managerConverter.toDto(manager)).toList();
    }

    @Operation(summary = "Find manager by ID",
            description = "Allows you to find a manager in the system by its ID")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/{id}")
    public ManagerDto getById(@PathVariable(name = "id") long id) {
        log.debug("Call method getById with id {}", id);
        return managerConverter.toDto(managerService.getById(id));
    }

    @Operation(summary = "Add manager",
            description = "Allows you to add a new manager in the system")
    @SecurityRequirement(name = "basicauth")
    @PostMapping
    public ResponseEntity<ManagerDto> addManager(@RequestBody ManagerDto manager) {
        log.debug("Call method addManager with manager {}", manager);
        return new ResponseEntity<>(managerConverter.toDto(
                managerService.add(managerConverter.toEntity(manager))), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete manager",
            description = "Allows you to delete an existing manager from the system")
    @SecurityRequirement(name = "basicauth")
    @DeleteMapping
    public void remove(@RequestBody ManagerDto manager) {
        log.debug("Call method remove with manager {}", manager);
        managerService.remove(managerService.getById(manager.getId()));
    }
}