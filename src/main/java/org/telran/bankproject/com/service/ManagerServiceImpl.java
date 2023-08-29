package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.exceptions.NotRemovedDependenciesException;
import org.telran.bankproject.com.repository.ManagerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    private static final Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Override
    public List<Manager> getAll() {
        log.debug("Call method findAll");
        List<Manager> all = managerRepository.findAll();
        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }

    @Override
    public Manager getById(long id) {
        Manager manager = managerRepository.findById(id).orElse(null);
        if (manager == null) throw new EntityNotFoundException(String.format("Manager with id %d not found", id));
        log.debug("Call method getReferenceById with id {}", id);
        return managerRepository.getReferenceById(id);
    }

    @Override
    public Manager add(Manager manager) {
        log.debug("Call method save with manager {}", manager);
        return managerRepository.save(manager);
    }

    @Override
    public void remove(Manager manager) {
        if (!manager.getClients().isEmpty())
            throw new NotRemovedDependenciesException("This manager still has clients");
        if (!manager.getProducts().isEmpty())
            throw new NotRemovedDependenciesException("This manager still has products");
        log.debug("Call method deleteAllByIdInBatch with id {}", manager.getId());
        managerRepository.deleteAllByIdInBatch(Collections.singleton(manager.getId()));
    }
}