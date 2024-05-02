package com.example.CSTestAssignment.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {
    @Test
    public void whenUserDTOInitialized_thenInitializedCorrectly(){
        UserDTO userDTO = new UserDTO();
        assertNotNull(userDTO);
    }
    @Test public void whenUserDTOInitializedWithParams_thenInitializedCorrectly(){

        String email = "test7@example.com";
        String name = "John7";
        String lastName = "Doe7";
        String birthday = "1997-01-01";
        String address = "127 Street";
        String phone = "122-456-7890";
        UserDTO userDTO = new UserDTO(email ,name,lastName,birthday,address,phone);
        assertNotNull(userDTO);
        assertEquals("test7@example.com",userDTO.getEmail());
        assertEquals("John7",userDTO.getFirstName());
        assertEquals("Doe7",userDTO.getLastName());
        assertEquals("1997-01-01",userDTO.getBirthday());
        assertEquals("127 Street",userDTO.getAddress());
        assertEquals("122-456-7890",userDTO.getPhone());
    }

}