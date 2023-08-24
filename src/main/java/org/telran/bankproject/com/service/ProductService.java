package org.telran.bankproject.com.service;

import org.telran.bankproject.com.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getById(long id);

    Product add(Product product);

    void remove(long id);
}