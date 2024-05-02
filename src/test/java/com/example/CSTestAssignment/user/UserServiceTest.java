package com.example.CSTestAssignment.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest

class UserServiceTest {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private  ValidationUser validator;
    @Autowired
    private UserService userService;
    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = new User("test7@example.com","John7","Doe7",LocalDate.parse("1997-01-01"),"127 Street","122-456-7890");

        repository.save(user);
        User foundUser = repository.findById(user.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getFirstName(), foundUser.getFirstName());
        assertEquals(user.getLastName(), foundUser.getLastName());
        assertEquals(user.getBirthday(), foundUser.getBirthday());
        assertEquals(user.getAddress(), foundUser.getAddress());
        assertEquals(user.getPhone(), foundUser.getPhone());

    }

    @Test
    void getAllUsers_shouldReturnListUsers() {
        User user = new User();
        user.setEmail("test1@example.com");
        user.setFirstName("John1");
        user.setLastName("Doe1");
        user.setBirthday(LocalDate.parse("1991-01-01"));
        user.setAddress("121 Street");
        user.setPhone("121-456-7890");

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setFirstName("John2");
        user2.setLastName("Doe2");
        user2.setBirthday(LocalDate.parse("1992-01-01"));
        user2.setAddress("122 Street");
        user2.setPhone("122-456-7890");

        repository.save(user);
        repository.save(user2);

        List <User > allUsers = userService.getAllUsers();
        assertFalse(allUsers.isEmpty());
        assertEquals(2,allUsers.size());

    }


    @Test
    void createUser_shouldReturnUser() {
        UserDTO userDTO = new UserDTO("test@example.com","John","Doe","1990-01-01","120 Street","120-456-7890");

        User userToData = userService.createUser(userDTO);
        User userFromData = repository.findById(userToData.getId()).orElse(null);
        assertNotNull(userFromData);
        assertEquals(userToData.getEmail(), userFromData.getEmail());
        assertEquals(userToData.getFirstName(), userFromData.getFirstName());
        assertEquals(userToData.getLastName(), userFromData.getLastName());
        assertEquals(userToData.getBirthday(), userFromData.getBirthday());
        assertEquals(userToData.getAddress(), userFromData.getAddress());
        assertEquals(userToData.getPhone(), userFromData.getPhone());
    }

    @Test
    void updateSomeFields_shouldReturnUser() {
        User user = new User();
        user.setEmail("test1@example.com");
        user.setFirstName("John1");
        user.setLastName("Doe1");
        user.setBirthday(LocalDate.parse("1991-01-01"));
        user.setAddress("121 Street");
        user.setPhone("121-456-7890");
        repository.save(user);

        Long userId = user.getId();
        String jsonString = "{\"email\":\"lmml@lkmlk.kk\",\"firstName\":\"dfvvfsdf\",\"birthday\":\"1998-01-03\"}";
        User updatedUser = userService.updateSomeFields(userId,jsonString);
        User userFromData = repository.findById(user.getId()).orElse(null);
        assertNotNull(userFromData);
        assertEquals(updatedUser.getEmail(), userFromData.getEmail());
        assertEquals(updatedUser.getId(), userFromData.getId());
        assertEquals(updatedUser.getFirstName(), userFromData.getFirstName());
        assertEquals(updatedUser.getLastName(), userFromData.getLastName());
        assertEquals(updatedUser.getBirthday(), userFromData.getBirthday());
        assertEquals(updatedUser.getAddress(), userFromData.getAddress());
        assertEquals(updatedUser.getPhone(), userFromData.getPhone());
    }
    @Test
    void updateSomeFields_shouldCatchJsonProcessingException(){
        User user = new User();
        Long userId = user.getId();
        String jsonString = "{\"email\"\"lmml@lkmlk.kk\",\"firstName\":\"dfvvfsdf\",\"birthday\":\"1998-01-03\"}";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateSomeFields(userId,jsonString);
        });
        assertEquals("Invalid json format", exception.getMessage());
    }

    @Test
    void updateUserById() {
        User user = new User();
        user.setEmail("test1@example.com");
        user.setFirstName("John1");
        user.setLastName("Doe1");
        user.setBirthday(LocalDate.parse("1991-01-01"));
        user.setAddress("121 Street");
        user.setPhone("121-456-7890");
        repository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setBirthday("1990-01-01");
        userDTO.setAddress("120 Street");
        userDTO.setPhone("120-456-7890");

        User userToData = userService.updateUserById(user.getId(),userDTO);
        User userFromData = repository.findById(userToData.getId()).orElse(null);
        assertNotNull(userFromData);
        assertEquals(userToData.getEmail(), userFromData.getEmail());
        assertEquals(userToData.getFirstName(), userFromData.getFirstName());
        assertEquals(userToData.getLastName(), userFromData.getLastName());
        assertEquals(userToData.getBirthday(), userFromData.getBirthday());
        assertEquals(userToData.getAddress(), userFromData.getAddress());
        assertEquals(userToData.getPhone(), userFromData.getPhone());

    }

    @Test
    void deleteUserById() {
        User user = new User();
        user.setEmail("test1@example.com");
        user.setFirstName("John1");
        user.setLastName("Doe1");
        user.setBirthday(LocalDate.parse("1991-01-01"));
        user.setAddress("121 Street");
        user.setPhone("121-456-7890");
        repository.save(user);

        assertTrue(repository.existsById(user.getId()));
        userService.deleteUserById(user.getId());

        assertFalse(repository.existsById(user.getId()));
    }

    @Test
    void searchByBirthdayFromTo_returnListUsers() {
        String fromDate = "1991-01-01";
        String toDate = "1992-01-01";

        User user = new User();
        user.setEmail("test1@example.com");
        user.setFirstName("John1");
        user.setLastName("Doe1");
        user.setBirthday(LocalDate.parse("1991-01-01"));
        user.setAddress("121 Street");
        user.setPhone("121-456-7890");

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setFirstName("John2");
        user2.setLastName("Doe2");
        user2.setBirthday(LocalDate.parse("1992-01-01"));
        user2.setAddress("122 Street");
        user2.setPhone("122-456-7890");

        repository.save(user);
        repository.save(user2);

        List <User > allUsers = userService.searchByBirthdayFromTo(fromDate,toDate);
        assertFalse(allUsers.isEmpty());
        assertTrue(allUsers.contains(user));
        assertTrue(allUsers.contains(user2));
    }
}