package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.repository.AgreementRepository;

import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private AgreementRepository agreementRepository;

    @Override
    public List<Agreement> getAll() {
        return agreementRepository.findAll();
    }

    @Override
    public Agreement getById(long id) {
        return agreementRepository.getReferenceById(id);
    }

    @Override
    public Agreement add(Agreement agreement) {
        agreement.getProduct().setAgreement(agreement);
        return agreementRepository.save(agreement);
    }

    @Override
    public void remove(long id) {
        agreementRepository.deleteById(id);
    }
}
