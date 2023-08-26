package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Agreement;

import java.util.List;

public interface AgreementService {

    List<Agreement> getAll();

    Agreement getById(long id);

    Agreement add(Agreement agreement);

    void remove(Agreement agreement);
}