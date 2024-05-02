package com.example.CSTestAssignment.services.utils;

import com.example.CSTestAssignment.client.ResponseUserDTO;
import com.example.CSTestAssignment.repository.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Component
public class MapperResponses {
    public ResponseEntity<ResponseUserDTO> buildSuccessResponse(String message, List<User> users) {
        ResponseUserDTO response = new ResponseUserDTO();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(users);
        return ResponseEntity.ok(response);
    }
   public ResponseEntity<ResponseUserDTO> buildSuccessResponseForUser(String message, User user){
       ResponseUserDTO response = new ResponseUserDTO();
       response.setSuccess(true);
       response.setMessage(message);
       response.setData(new ArrayList<>());
       response.getData().add(user);
       return ResponseEntity.ok(response);
   }
   public ResponseEntity<ResponseUserDTO> buildSuccessResponseForMessage(String message){
       ResponseUserDTO response = new ResponseUserDTO();
       response.setSuccess(true);
       response.setMessage(message);
       return ResponseEntity.ok(response);
   }
   public ResponseEntity<ResponseUserDTO> buildErrorResponse(String errorMessage) {
       ResponseUserDTO response = new ResponseUserDTO();
       response.setSuccess(false);
       response.setMessage(errorMessage);
       return ResponseEntity.badRequest().body(response);
   }
}
