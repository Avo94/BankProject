package org.telran.bankproject.com.dto;

public class ClientDto {

    private long id;
    private int status;
    private String firstName;
    private String lastName;
    private String email;

    public ClientDto(long id, int status, String firstName, String lastName, String email) {
        this.id = id;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public ClientDto() {
        //
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}