package org.telran.bankproject.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.bankproject.com.entity.Product;
import org.telran.bankproject.com.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

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
        log.debug("Call method save with product {}", product);
        return productRepository.save(product);
    }

    @Override
    public void remove(Product product) {
        log.debug("Call method delete with product id {}", product.getId());
        productRepository.delete(product);
    }
}