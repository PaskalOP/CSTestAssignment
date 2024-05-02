package com.example.CSTestAssignment.client;

import com.example.CSTestAssignment.repository.User;
import lombok.Data;

import java.util.List;


@Data
public class ResponseUserDTO {
   private boolean success;
   private String message;
   private List<User> data;


}
