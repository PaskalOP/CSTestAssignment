package com.example.CSTestAssignment.user;

import lombok.Data;
@Data
public class UserDTO {

    private String email ;
    private String firstName ;
    private String lastName;
    private String birthday;
    private String address;
    private String phone;

    public UserDTO(){}

    public UserDTO(String email, String firstName, String lastName, String birthday, String address, String phone) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
    }
}
