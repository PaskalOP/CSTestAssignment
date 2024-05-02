package com.example.CSTestAssignment.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private long id;

    @Column(nullable = false)
    private String email ;

    @Column(name= "first_name",nullable = false)
    private String firstName ;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column( nullable = false)
    private LocalDate birthday;

    @Column
    private String address;

    @Column
    private String phone;
    public User(){
        this.id = UUID.randomUUID().getLeastSignificantBits()*-1;
    }


    public User(String email, String firstName, String lastName, LocalDate birthday, String address, String phone) {
        this.id = UUID.randomUUID().getLeastSignificantBits()*-1;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
    }
}
