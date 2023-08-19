package org.telran.bankproject.com.dto;

import org.telran.bankproject.com.entity.Account;
import org.telran.bankproject.com.entity.Manager;

import java.util.List;

public class ClientDto {

    private Manager managerId;
    private List<Account> accounts;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public ClientDto(Manager managerId, List<Account> accounts, String firstName, String lastName, String email, String phone) {
        this.managerId = managerId;
        this.accounts = accounts;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public ClientDto() {
        //
    }

    public Manager getManagerId() {
        return managerId;
    }

    public void setManagerId(Manager managerId) {
        this.managerId = managerId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}