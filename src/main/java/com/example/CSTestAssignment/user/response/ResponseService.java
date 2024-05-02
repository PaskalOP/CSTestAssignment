package com.example.CSTestAssignment.user.response;

import com.example.CSTestAssignment.user.User;
import com.example.CSTestAssignment.user.UserDTO;
import com.example.CSTestAssignment.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    @Autowired
    private MapperResponses mapperResponses;
    @Autowired
    private UserService userService;
    public ResponseEntity<ResponseUserDTO> getUserByIdResponse(long id){
        try{
            User user = userService.getUserById(id);
            if(user == null) {
                return  mapperResponses.buildErrorResponse("No one user with this ID");
            }
            return mapperResponses.buildSuccessResponseForUser("User data", user);
        } catch (IllegalArgumentException e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        } catch (Exception e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        }

    }

    public ResponseEntity<ResponseUserDTO> getAllUsersResponse(){
        try {
            List<User> allUsers = userService.getAllUsers();
            if(allUsers == null) {
                return  mapperResponses.buildErrorResponse("All users is null");
            }
            return mapperResponses.buildSuccessResponse("Users data", allUsers );
        } catch (IllegalArgumentException e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        } catch (Exception e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        }
    }
    public ResponseEntity<ResponseUserDTO> saveUserResponse (UserDTO userFromRequest){
        try {
            User updatedUser = userService.createUser(userFromRequest);
            return mapperResponses.buildSuccessResponseForUser("User create successfully", updatedUser);
        } catch (IllegalArgumentException e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        } catch (Exception e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        }
    }
    public ResponseEntity<ResponseUserDTO> updateUserByIdResponse(long id, UserDTO user){
        try {
            if(userService.getUserById(id) == null){
                return  mapperResponses.buildErrorResponse("No one user with this ID");
            }
            User updatedUser = userService.updateUserById(id, user);

            return mapperResponses.buildSuccessResponseForUser("User updated successfully", updatedUser );
        } catch (IllegalArgumentException e) {
            return mapperResponses.buildErrorResponse(e.getMessage());
        } catch (Exception e) {
            return mapperResponses.buildErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<ResponseUserDTO> updateUserFieldsResponse(long id, String jsonQuery) {
        try {
            User updatedUser = userService.updateSomeFields(id, jsonQuery);
            if(updatedUser == null) {
                return  mapperResponses.buildErrorResponse("No one user with this ID");
            }
            return mapperResponses.buildSuccessResponseForUser("All fields updated successfully", updatedUser);
        } catch (IllegalArgumentException e) {
            return mapperResponses.buildErrorResponse(e.getMessage());
        } catch (Exception e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        }
    }
    public ResponseEntity<ResponseUserDTO> deleteUserByIdResponse(long id){
        try {
            if(userService.getUserById(id) == null) {
                return  mapperResponses.buildErrorResponse("No one user with this ID");
            }
            userService.deleteUserById(id);
            return mapperResponses.buildSuccessResponseForMessage("User delete successfully");
        } catch (IllegalArgumentException e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        } catch (Exception e) {
            return mapperResponses.buildErrorResponse("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<ResponseUserDTO> searchByBirthdayFromToResponse(String fromDate, String toDate){
        try {
            List <User> users = userService.searchByBirthdayFromTo(fromDate,toDate);
            return mapperResponses.buildSuccessResponse("Searched users", users);
        } catch (IllegalArgumentException e) {
            return mapperResponses.buildErrorResponse( e.getMessage());
        } catch (Exception e) {
            return mapperResponses.buildErrorResponse("An error occurred: " + e.getMessage());
        }
    }

}
