package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.repository.AgreementRepository;

import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {

    private static final Logger log = LoggerFactory.getLogger(AgreementServiceImpl.class);

    @Autowired
    private AgreementRepository agreementRepository;
    @Override
    public List<Agreement> getAll() {
        log.debug("Call method findAll");
        List<Agreement> all = agreementRepository.findAll();

        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }
    @Override
    public Agreement getById(long id) {
        log.debug("Call method getReferenceById with id {}", id);
        return agreementRepository.getReferenceById(id);
    }

    @Override
    public Agreement add(Agreement agreement) {
        log.debug("Call method save with agreement {}", agreement);
        return agreementRepository.save(agreement);
    }

    @Override
    public void remove(Agreement agreement) {
        log.debug("Call method delete with agreement id {}", agreement.getId());
        agreementRepository.delete(agreement);
    }
}
