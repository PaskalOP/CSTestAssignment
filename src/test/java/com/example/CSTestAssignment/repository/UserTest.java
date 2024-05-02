package com.example.CSTestAssignment.repository;

import com.example.CSTestAssignment.repository.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    public void whenUserInitialized_thenInitializedCorrectly(){
        User user = new User();
        assertNotNull(user);
    }
    @Test
    public void whenUserInitializedWithParams_thenInitializedCorrectly(){

        String email = "test7@example.com";
        String name = "John7";
        String lastName = "Doe7";
        String birthday = "1997-01-01";
        String address = "127 Street";
        String phone = "122-456-7890";
        User user = new User(email ,name,lastName, LocalDate.parse(birthday),address,phone);
        assertNotNull(user );
        assertEquals("test7@example.com",user.getEmail());
        assertEquals("John7",user.getFirstName());
        assertEquals("Doe7",user.getLastName());
        assertEquals(LocalDate.parse("1997-01-01"),user.getBirthday());
        assertEquals("127 Street",user.getAddress());
        assertEquals("122-456-7890",user.getPhone());
    }

}