package com.example.CSTestAssignment.user.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseUserDTOTest {
    @Test
    public void whenResponseUserDTOInitialized_thenInitializedCorrectly(){
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        assertNotNull(responseUserDTO);
    }

}