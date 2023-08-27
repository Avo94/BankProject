package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Client;
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

    @Override
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getById(long id) {
        Manager manager = managerRepository.findById(id).orElse(null);
        if (manager == null) throw new EntityNotFoundException(String.format("Manager with id %d not found", id));
        return managerRepository.getReferenceById(id);
    }

    @Override
    public Manager add(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public void remove(Manager manager) {
        if (!manager.getClients().isEmpty())
            throw new NotRemovedDependenciesException("This manager still has clients");
        if (!manager.getProducts().isEmpty())
            throw new NotRemovedDependenciesException("This manager still has products");
        managerRepository.deleteAllByIdInBatch(Collections.singleton(manager.getId()));
    }
}