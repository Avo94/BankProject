package org.telran.bankproject.com.entity;

import org.telran.bankproject.com.enums.Status;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "manager")
    private List<Client> clients = new ArrayList<>();
    @OneToMany(mappedBy = "manager")
    private List<Product> products = new ArrayList<>();
    private String firstName;
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Manager(long id, List<Client> clients, List<Product> products, String firstName,
                   String lastName, Status status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.clients = clients;
        this.products = products;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Manager() {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}