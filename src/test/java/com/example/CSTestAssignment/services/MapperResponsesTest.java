package com.example.CSTestAssignment.services;

import com.example.CSTestAssignment.services.utils.MapperResponses;
import com.example.CSTestAssignment.client.ResponseUserDTO;
import com.example.CSTestAssignment.repository.User;
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
        User user1 = new User();
        User user2 = new User();
        List<User> usersFromData = new ArrayList<>();
        usersFromData.add(user1);
        usersFromData.add(user2);

        ResponseEntity<ResponseUserDTO> response = mapperResponses.buildSuccessResponse("message",usersFromData);
        buildTestTrue("message",200,usersFromData, response);
    }

    @Test
    void buildSuccessResponse_returnResponseEntitySuccessWithUser() {
        User user1 = new User();
        List<User> usersFromData = new ArrayList<>();
        usersFromData.add(user1);

        ResponseEntity<ResponseUserDTO> response = mapperResponses.buildSuccessResponseForUser("message",user1);
        buildTestTrue("message",200,usersFromData,response);
    }

    @Test
    void testBuildSuccessResponse_returnResponseEntitySuccessWithMessage() {
        String message = "message";
        ResponseEntity<ResponseUserDTO> response = mapperResponses.buildSuccessResponseForMessage(message);
        buildTestTrue("message",200,null,response);
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
    private void buildTestTrue(String message,int statusCode,List<User> users, ResponseEntity<ResponseUserDTO> response){
        ResponseUserDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(statusCode, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertTrue(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());
        assertEquals(users, responseBody.getData());
    }
}