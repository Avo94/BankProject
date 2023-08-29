package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Agreement;
import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.enums.Status;
import org.telran.bankproject.com.repository.AccountRepository;
import org.telran.bankproject.com.repository.ProductRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private AccountRepository accountRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<Product> getAll() {
        log.debug("Call method findAll");
        List<Product> all = productRepository.findAll();
        log.debug("Method findAll returned List with size {}", all.size());
        return all;
    }

    @Override
    public Product getById(long id) {
        log.debug("Call method getReferenceById with id {}", id);
        return productRepository.getReferenceById(id);
    }

    @Override
    public Product add(Product product) {
        log.debug("Call method add with product {}", product);
        Product entity = productRepository.save(product);
        entity.getManager().setProducts(List.of(product));
        Long lastId;
        if (agreementService.getAll().isEmpty()) {
            lastId = 0L;
        } else {
            lastId = agreementService.getAll().stream().map(Agreement::getId)
                    .max(Comparator.naturalOrder()).orElse(null);
        }
        Account account = product.getManager().getClients().stream().map(Client::getAccounts)
                .flatMap(Collection::stream).filter(x -> x.getId() == accountRepository.findAll()
                        .stream().map(Account::getId).max(Comparator.naturalOrder()).get()).findFirst().get();
        agreementService.add(new Agreement(lastId + 1, account, entity,
                entity.getInterestRate(), Status.ACTIVE, account.getBalance(),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
        new Agreement(lastId + 1, account, product, product.getInterestRate(),
                Status.ACTIVE, account.getBalance(), new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));
        return entity;
    }

    @Override
    public void remove(Product product) {
        log.debug("Call method deleteAllByIdInBatch with product id {}", product.getId());
        productRepository.deleteAllByIdInBatch(Collections.singleton(product.getId()));
    }
}