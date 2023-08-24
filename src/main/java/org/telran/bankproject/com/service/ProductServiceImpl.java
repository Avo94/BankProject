package org.telran.bankproject.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.repository.ProductRepository;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AgreementService agreementService;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(long id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public Product add(Product product) {
        Long lastAgreementId = agreementService.getAll().stream().map(Agreement::getId)
                .max(Comparator.naturalOrder()).orElse(null);
        Agreement agreement = agreementService.add(new Agreement(lastAgreementId + 1, null, product, 8,
                Status.ACTIVE, 0, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));
        product.setAgreement(agreement);
        return productRepository.save(product);
    }

    @Override
    public void remove(long id) {
        productRepository.deleteById(id);
    }
}