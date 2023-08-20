package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Client;
import org.telran.bankproject.com.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ManagerDto {

    private long id;
    private List<Client> clients = new ArrayList<>();
    private Product product;
    private String firstName;
    private String lastName;
    private int status;

    public ManagerDto(long id, List<Client> clients, Product product, String firstName, String lastName, int status) {
        this.id = id;
        this.clients = clients;
        this.product = product;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public ManagerDto() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}