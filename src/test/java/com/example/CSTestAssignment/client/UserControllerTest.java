package com.example.CSTestAssignment.client;

import com.example.CSTestAssignment.repository.User;
import com.example.CSTestAssignment.repository.UserRepository;
import com.example.CSTestAssignment.services.ResponseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResponseService responseService;
    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers() throws Exception {
        User user1 = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        User user2 = new User("test2@example.com","John2","Doe2",LocalDate.parse("1992-01-01"),"122 Street","122-456-7890");
        userRepository.save(user1);
        userRepository.save(user2);

        String expectedJson = String.format( "{\"success\":true,\"message\":\"Users data\",\"data\":[{\"id\":%s,\"email\":\"test@example.com\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthday\":\"1990-01-01\",\"address\":\"120 Street\",\"phone\":\"120-456-7890\"},{\"id\":%s,\"email\":\"test2@example.com\",\"firstName\":\"John2\",\"lastName\":\"Doe2\",\"birthday\":\"1992-01-01\",\"address\":\"122 Street\",\"phone\":\"122-456-7890\"}]}",user1.getId(),user2.getId());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(expectedJson, result.getResponse()
                .getContentAsString());
    }


    @Test
    void getUser() throws Exception {
        User user = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        user.setId(1L);
        userRepository.save(user);
        List<User> users = new ArrayList<>();
        users.add(user);

        String expectedJson = String.format( "{\"success\":true,\"message\":\"User data\",\"data\":[{\"id\":%s,\"email\":\"test@example.com\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthday\":\"1990-01-01\",\"address\":\"120 Street\",\"phone\":\"120-456-7890\"}]}",user.getId());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(expectedJson, result.getResponse()
                .getContentAsString());

    }

    @Test
    void createNewUser() throws Exception {

        String jsonDTO = String.format( "{\"email\":\"test@example.com\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthday\":\"1990-01-01\",\"address\":\"120 Street\",\"phone\":\"120-456-7890\"}");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/save")
                        .content(jsonDTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        JsonNode responseJson = objectMapper.readTree(result.getResponse()
                .getContentAsString());

        assertEquals("true", responseJson.get("success")
                .asText());

        assertEquals("User create successfully", responseJson.get("message")
                .asText());
        assertTrue(responseJson.has("data"));
        JsonNode userFields = responseJson.get("data");

        assertEquals(1, userFields.size());

        assertEquals("test@example.com", userFields.get(0)
                .get("email")
                .asText());
        assertEquals("John", userFields.get(0)
                .get("firstName")
                .asText());
        assertEquals("Doe", userFields.get(0)
                .get("lastName")
                .asText());
        assertEquals("1990-01-01", userFields.get(0)
                .get("birthday")
                .asText());
        assertEquals("120 Street", userFields.get(0)
                .get("address")
                .asText());
        assertEquals("120-456-7890", userFields.get(0)
                .get("phone")
                .asText());


    }

    @Test
    void updateUser() throws Exception {
        User user = new User("test2@example.com","John2","Doe2",LocalDate.parse("1992-01-01"),"122 Street","122-456-7890");
        user.setId(1L);
        userRepository.save(user);

        String jsonDTO = String.format( "{\"email\":\"test@example.com\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthday\":\"1990-01-01\",\"address\":\"120 Street\",\"phone\":\"120-456-7890\"}");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/users/update/1")
                        .content(jsonDTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        JsonNode responseJson = objectMapper.readTree(result.getResponse()
                .getContentAsString());

        assertEquals("true", responseJson.get("success")
                .asText());

        assertEquals("User updated successfully", responseJson.get("message")
                .asText());
        assertTrue(responseJson.has("data"));
        JsonNode userFields = responseJson.get("data");

        assertEquals(1, userFields.size());

        assertEquals("test@example.com", userFields.get(0)
                .get("email")
                .asText());
        assertEquals("John", userFields.get(0)
                .get("firstName")
                .asText());
        assertEquals("Doe", userFields.get(0)
                .get("lastName")
                .asText());
        assertEquals("1990-01-01", userFields.get(0)
                .get("birthday")
                .asText());
        assertEquals("120 Street", userFields.get(0)
                .get("address")
                .asText());
        assertEquals("120-456-7890", userFields.get(0)
                .get("phone")
                .asText());
    }

    @Test
    void updateSomeFields() throws Exception {
        User user = new User("test2@example.com","John2","Doe2",LocalDate.parse("1992-01-01"),"122 Street","122-456-7890");
        user.setId(1L);
        userRepository.save(user);

        String jsonDTO = String.format( "{\"email\":\"test@example.com\",\"firstName\":\"John\"}");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/v1/users/update-some-fields/1")
                        .content(jsonDTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        JsonNode responseJson = objectMapper.readTree(result.getResponse()
                .getContentAsString());

        assertEquals("true", responseJson.get("success")
                .asText());

        assertEquals("All fields updated successfully", responseJson.get("message")
                .asText());
        assertTrue(responseJson.has("data"));
        JsonNode userFields = responseJson.get("data");

        assertEquals(1, userFields.size());

        assertEquals("test@example.com", userFields.get(0)
                .get("email")
                .asText());
        assertEquals("John", userFields.get(0)
                .get("firstName")
                .asText());
        assertEquals("Doe2", userFields.get(0)
                .get("lastName")
                .asText());
        assertEquals("1992-01-01", userFields.get(0)
                .get("birthday")
                .asText());
        assertEquals("122 Street", userFields.get(0)
                .get("address")
                .asText());
        assertEquals("122-456-7890", userFields.get(0)
                .get("phone")
                .asText());
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User("test2@example.com","John2","Doe2",LocalDate.parse("1992-01-01"),"122 Street","122-456-7890");
        user.setId(1L);
        userRepository.save(user);

        String expectedJson = String.format( "{\"success\":true,\"message\":\"User delete successfully\",\"data\":null}");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/users/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(expectedJson, result.getResponse()
                .getContentAsString());
    }

    @Test
    void searchByAge() throws Exception {
        User user1 = new User("test@example.com","John","Doe",LocalDate.parse("1990-01-01"),"120 Street","120-456-7890");
        User user2 = new User("test2@example.com","John2","Doe2",LocalDate.parse("1992-01-01"),"122 Street","122-456-7890");
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = new ArrayList<>();
        users.add(user1);

        String expectedJson = String.format( "{\"success\":true,\"message\":\"Searched users\",\"data\":[{\"id\":%s,\"email\":\"test@example.com\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthday\":\"1990-01-01\",\"address\":\"120 Street\",\"phone\":\"120-456-7890\"}]}",user1.getId());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("from","1990-01-01");
        params.add("to", "1991-01-01");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/search-by-age")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(expectedJson, result.getResponse()
                .getContentAsString());
    }
}