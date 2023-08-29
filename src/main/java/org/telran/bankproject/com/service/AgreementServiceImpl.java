package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.repository.AgreementRepository;

import java.util.Collections;
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
    private static final Logger log = LoggerFactory.getLogger(AgreementServiceImpl.class);

    @Override
    public Agreement add(Agreement agreement) {
        log.debug("Call method add with agreement {}", agreement);
        Agreement entity = agreementRepository.save(agreement);
        entity.getProduct().setAgreement(agreement);
        entity.getAccount().setAgreement(agreement);
        return entity;
    }

    @Override
    public void remove(Agreement agreement) {
        log.debug("Call method deleteAllByIdInBatch with agreement id {}", agreement.getId());
        agreementRepository.deleteAllByIdInBatch(Collections.singleton(agreement.getId()));
    }
}
