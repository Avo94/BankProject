package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Manager;
import org.telran.bankproject.com.repository.ManagerRepository;

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
        return managerRepository.getReferenceById(id);
    }

    @Override
    public Manager add(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public void remove(long id) {
        managerRepository.deleteById(id);
    }
}