package com.example.CSTestAssignment.user.response;

import com.example.CSTestAssignment.user.User;
import com.example.CSTestAssignment.user.UserDTO;
import com.example.CSTestAssignment.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional

class ResponseServiceTest {
    @Autowired
    ResponseService service;
    @Autowired
    UserRepository repository;
    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    void getUserByIdResponse_returnUserCorrect() {
        User user = new User();
        user.setEmail("test2@example.com");
        user.setFirstName("John2");
        user.setLastName("Doe2");
        user.setBirthday(LocalDate.parse("1992-01-01"));
        user.setAddress("122 Street");
        user.setPhone("122-456-7890");

        repository.save(user);
        List<User> usersFromData = new ArrayList<>();
        usersFromData.add(user);
        String message = "User data";

        ResponseEntity<ResponseUserDTO> response = service.getUserByIdResponse(user.getId());
        ResponseUserDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertTrue(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());
        assertEquals(usersFromData, responseBody.getData());
    }

    @Test
    void whenIdNotCorrect_getUserByIdResponse_returnErrorResponse(){
        long someId = 1L;
        String message = "No one user with this ID";
        ResponseEntity<ResponseUserDTO> response =service.getUserByIdResponse(someId);
        ResponseUserDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertFalse(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());
        assertEquals(null, responseBody.getData());

    }

    @Test
    void getAllUsersResponse_returnSuccessResponse() {

        User user1 = new User();
        user1.setEmail("test5@example.com");
        user1.setFirstName("John5");
        user1.setLastName("Doe5");
        user1.setBirthday(LocalDate.parse("1995-01-01"));
        user1.setAddress("125 Street");
        user1.setPhone("125-456-7890");

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setFirstName("John2");
        user2.setLastName("Doe2");
        user2.setBirthday(LocalDate.parse("1992-01-01"));
        user2.setAddress("122 Street");
        user2.setPhone("122-456-7890");

        String message ="Users data";

        repository.save(user1);
        repository.save(user2);

        List<User> usersFromData = new ArrayList<>();
        usersFromData.add(user1);
        usersFromData.add(user2);

        ResponseEntity<ResponseUserDTO> response = service.getAllUsersResponse();
        ResponseUserDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertTrue(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());

         assertEquals(usersFromData, responseBody.getData());
         assertEquals(2,responseBody.getData().size());

    }
    @Test
    void getAllUsersResponse_returnSuccessResponse_withNullData(){
        List<User> usersFromData = new ArrayList<>();
        String message ="Users data";

        ResponseEntity<ResponseUserDTO> response = service.getAllUsersResponse();
        ResponseUserDTO responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getHeaders().isEmpty());
        assertTrue(responseBody.isSuccess());
        assertEquals(message, responseBody.getMessage());

        assertEquals(usersFromData, responseBody.getData());
        assertEquals(0,responseBody.getData().size());
    }


    @Test
    void saveUserResponse_returnSuccessResponse() {
        UserDTO userDTO = new UserDTO("test@example.com","John","Doe","1990-01-01","120 Street","120-456-7890");
        ResponseEntity<ResponseUserDTO> response = service.saveUserResponse(userDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User create successfully",response.getBody().getMessage());
    }
    @Test
    void saveUserResponse_returnErrorResponse() {
        UserDTO userDTO = new UserDTO("testexample.com","John","Doe","1990-01-01","120 Street","120-456-7890");
        UserDTO userDTO2 = new UserDTO("teste@xample.com","","Doe","1990-01-01","120 Street","120-456-7890");
        UserDTO userDTO3 = new UserDTO("teste@xample.com","Joy","","1990-01-01","120 Street","120-456-7890");
        UserDTO userDTO4 = new UserDTO("teste@xample.com","Joy","Doe","1990.01.01","120 Street","120-456-7890");
        ResponseEntity<ResponseUserDTO> response = service.saveUserResponse(userDTO);
        ResponseEntity<ResponseUserDTO> response2 = service.saveUserResponse(userDTO2);
        ResponseEntity<ResponseUserDTO> response3 = service.saveUserResponse(userDTO3);
        ResponseEntity<ResponseUserDTO> response4 = service.saveUserResponse(userDTO4);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertFalse(response2.getBody().isSuccess());
        assertFalse(response3.getBody().isSuccess());
        assertFalse(response4.getBody().isSuccess());
    }

    @Test
    void updateUserByIdResponse_returnSuccessResponse() {
        User user = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        repository.save(user);
        UserDTO userDTO = new UserDTO("test@example.com","John","Doe","1990-01-01","120 Street","120-456-7890");
        ResponseEntity<ResponseUserDTO> response = service.updateUserByIdResponse(user.getId(),userDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());

    }
    @Test
    void updateUserByIdResponse_returnErrorResponse() {
        User user = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        repository.save(user);
        UserDTO userDTO = new UserDTO("testexample.com","John","Doe","1990-01-01","120 Street","120-456-7890");
        UserDTO userDTO2 = new UserDTO("teste@xample.com","","Doe","1990-01-01","120 Street","120-456-7890");
        UserDTO userDTO3 = new UserDTO("teste@xample.com","Joy","","1990-01-01","120 Street","120-456-7890");
        UserDTO userDTO4 = new UserDTO("teste@xample.com","Joy","Doe","1990.01.01","120 Street","120-456-7890");
        ResponseEntity<ResponseUserDTO> response = service.updateUserByIdResponse(user.getId(),userDTO);
        ResponseEntity<ResponseUserDTO> response2 = service.updateUserByIdResponse(user.getId(),userDTO2);
        ResponseEntity<ResponseUserDTO> response3 = service.updateUserByIdResponse(user.getId(),userDTO3);
        ResponseEntity<ResponseUserDTO> response4 = service.updateUserByIdResponse(user.getId(),userDTO4);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertFalse(response2.getBody().isSuccess());
        assertFalse(response3.getBody().isSuccess());
        assertFalse(response4.getBody().isSuccess());

    }
    @Test
    void updateUserFieldsResponse_returnSuccessResponse() {
        User user = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        repository.save(user);
        String jsonString = "{\"email\":\"lmml@lkmlk.kk\",\"firstName\":\"dfvvfsdf\",\"birthday\":\"1998-01-03\"}";
        ResponseEntity<ResponseUserDTO> response = service.updateUserFieldsResponse(user.getId(),jsonString);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("All fields updated successfully",response.getBody().getMessage());
    }
    @Test
    void updateUserFieldsResponse_returnErrorResponse() {
        User user = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        repository.save(user);
        String jsonString = "{\"email\"\"lmml@lkmlk.kk\",\"firstName\":\"dfvvfsdf\",\"birthday\":\"1998-01-03\"}";
        String jsonString2 = "{\"email\":\"lmmllkmlk.kk\",\"firstName\":\"dfvvfsdf\",\"birthday\":\"1998-01-03\"}";
        String jsonString3 = "{\"email\":\"lmml@lkmlk.kk\",\"birthday\":\"1998.01.03\"}";
        ResponseEntity<ResponseUserDTO> response = service.updateUserFieldsResponse(user.getId(),jsonString);
        ResponseEntity<ResponseUserDTO> response2 = service.updateUserFieldsResponse(user.getId(),jsonString2);
        ResponseEntity<ResponseUserDTO> response3 = service.updateUserFieldsResponse(user.getId(),jsonString3);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertFalse(response2.getBody().isSuccess());
        assertFalse(response3.getBody().isSuccess());

    }
    //public ResponseEntity<ResponseUserDTO> deleteUserByIdResponse(long id)
    @Test
    void deleteUserByIdResponse_returnSuccessResponse() {
        User user = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        repository.save(user);
        ResponseEntity<ResponseUserDTO> response = service.deleteUserByIdResponse(user.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User delete successfully",response.getBody().getMessage());
    }

    @Test
    void deleteUserByIdResponse_returnErrorResponse() {
        ResponseEntity<ResponseUserDTO> response = service.deleteUserByIdResponse(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
    }
    //ResponseEntity<ResponseUserDTO> searchByBirthdayFromToResponse(String fromDate, String toDate)
    @Test
    void searchByBirthdayFromToResponse_returnSuccessResponse() {
        String fromDate = "1990-01-01";
        String toString = "1991-01-01";

        ResponseEntity<ResponseUserDTO> response = service.searchByBirthdayFromToResponse(fromDate,toString);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Searched users",response.getBody().getMessage());
        assertTrue(response.getBody().isSuccess());
    }
    @Test
    void searchByBirthdayFromToResponse_returnErrorResponse() {
        String fromDate = "1990.01.01";
        String toString = "1991-01-01";
        ResponseEntity<ResponseUserDTO> response = service.searchByBirthdayFromToResponse(fromDate,toString);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
    }
}