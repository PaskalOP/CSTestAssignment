package com.example.CSTestAssignment.user.response;

import com.example.CSTestAssignment.user.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ResponseUserDTO {
   private boolean success;
   private String message;
   private List<User> data;


}
