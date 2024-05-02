package com.example.CSTestAssignment.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class UserMapperTest {
    @Autowired
    private ValidationUser validator;
    @Autowired
    private  UserMapper userMapper;



    @Test
    void toUser_validUserDTO_shouldReturnUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setBirthday("1990-01-01");
        userDTO.setAddress("123 Street");
        userDTO.setPhone("123-456-7890");

        User user = userMapper.toUser(userDTO);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(LocalDate.parse("1990-01-01"), user.getBirthday());
        assertEquals("123 Street", user.getAddress());
        assertEquals("123-456-7890", user.getPhone());
    }
    @Test
    void toUser_validUserDTO_shouldThrowNullPointerException(){
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            userMapper.toUser(null);
        });
        assertEquals("userDTO is null", exception.getMessage());
    }

    @Test
    void toUserFromAllFields_shouldReturnUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setBirthday("1990-01-01");
        userDTO.setAddress("123 Street");
        userDTO.setPhone("123-456-7890");

        User user = new User();
        user.setEmail("test2@example.com");
        user.setFirstName("John2");
        user.setLastName("Doe2");
        user.setBirthday(LocalDate.parse("1992-01-01"));
        user.setAddress("122 Street");
        user.setPhone("122-456-7890");

        User changedUser = userMapper.toUserFromAllFields(userDTO,user);
        assertNotNull(changedUser);
        assertEquals("test@example.com", changedUser.getEmail());
        assertEquals("John", changedUser.getFirstName());
        assertEquals("Doe", changedUser.getLastName());
        assertEquals(LocalDate.parse("1990-01-01"), changedUser.getBirthday());
        assertEquals("123 Street", changedUser.getAddress());
        assertEquals("123-456-7890", changedUser.getPhone());
    }
    @Test
    void toUserFromAllFields_shouldThrowNullPointerException(){
        UserDTO userDTO = new UserDTO();
        User user = new User();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            userMapper.toUserFromAllFields(userDTO,null);
            userMapper.toUserFromAllFields(null,null);
            userMapper.toUserFromAllFields(null,user);
        });
        assertEquals("Some of params is null", exception.getMessage());
    }

    @Test
    void toUserFromSomeFields_shouldReturnUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("John");
        userDTO.setBirthday("1990-01-01");


        User user = new User();
        user.setEmail("test2@example.com");
        user.setFirstName("John2");
        user.setLastName("Doe2");
        user.setBirthday(LocalDate.parse("1992-01-01"));
        user.setAddress("122 Street");
        user.setPhone("122-456-7890");

        User changedUser = userMapper.toUserFromSomeFields(userDTO,user);
        assertNotNull(changedUser);
        assertEquals("test@example.com", changedUser.getEmail());
        assertEquals("John", changedUser.getFirstName());
        assertEquals("Doe2", changedUser.getLastName());
        assertEquals(LocalDate.parse("1990-01-01"), changedUser.getBirthday());
        assertEquals("122 Street", changedUser.getAddress());
        assertEquals("122-456-7890", changedUser.getPhone());

    }
    @Test
    void toUserFromSomeFields_shouldThrowNullPointerException(){
        UserDTO userDTO = new UserDTO();
        User user = new User();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            userMapper.toUserFromSomeFields(userDTO,null);
            userMapper.toUserFromSomeFields(null,null);
            userMapper.toUserFromSomeFields(null,user);
        });
        assertEquals("Some of params is null", exception.getMessage());
    }
}