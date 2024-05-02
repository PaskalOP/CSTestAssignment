package com.example.CSTestAssignment.user.response;

import com.example.CSTestAssignment.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class MapperResponsesTest {
    @Autowired
    private MapperResponses mapperResponses;

    @Test
    void buildSuccessResponse_returnResponseEntitySuccessWithList() {
        String message = "message";
        User user1 = new User();
        User user2 = new User();
        List<User> usersFromData = new ArrayList<>();
        usersFromData.add(user1);
        usersFromData.add(user2);
        ResponseEntity<ResponseUserDTO> response = mapperResponses.buildSuccessResponse(message,usersFromData);
        ResponseUserDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertTrue(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());
        assertEquals(usersFromData, responseBody.getData());
    }

    @Test
    void buildSuccessResponse_returnResponseEntitySuccessWithUser() {
        String message = "message";
        User user1 = new User();
        List<User> usersFromData = new ArrayList<>();
        usersFromData.add(user1);
        ResponseEntity<ResponseUserDTO> response = mapperResponses.buildSuccessResponseForUser(message,user1);
        ResponseUserDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertTrue(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());
        assertEquals(usersFromData, responseBody.getData());


    }

    @Test
    void testBuildSuccessResponse_returnResponseEntitySuccessWithMessage() {
        String message = "message";
        ResponseEntity<ResponseUserDTO> response = mapperResponses.buildSuccessResponseForMessage(message);
        ResponseUserDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertTrue(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());
        assertEquals( null, responseBody.getData());
    }

    @Test
    void buildErrorResponse() {
        String message = "error";
        ResponseEntity<ResponseUserDTO> response = mapperResponses.buildErrorResponse(message);
        ResponseUserDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertFalse(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());
        assertEquals( null, responseBody.getData());

    }
}