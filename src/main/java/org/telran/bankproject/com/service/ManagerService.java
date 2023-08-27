package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getAll();

    Manager getById(long id);

    Manager add(Manager manager);

    void remove(Manager manager);
}