package org.telran.bankproject.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.telran.bankproject.com.enums.Status;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagerDto {

    private long id;
    private List<ClientDto> clients = new ArrayList<>();
    private List<ProductDto> products = new ArrayList<>();
    private String firstName;
    private String lastName;
    @Schema(description = "ACTIVE of INACTIVE", defaultValue = "ACTIVE")
    private Status status;

    public ManagerDto(long id, List<ClientDto> clients, List<ProductDto> products, String firstName,
                      String lastName, Status status) {
        this.id = id;
        this.clients = clients;
        this.products = products;
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

    public List<ClientDto> getClients() {
        return clients;
    }

    public void setClients(List<ClientDto> clients) {
        this.clients = clients;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
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
}