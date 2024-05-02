package com.example.CSTestAssignment.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    @Autowired
    private  ValidationUser validator;

    public User getUserById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User createUser ( UserDTO userDTO)  {
        User userToDataBase = userMapper.toUser(userDTO);
        repository.save(userToDataBase);
        return userToDataBase;
    }

    public User updateSomeFields(Long userId, String jsonDataFromRequest) {
        User user = repository.findById(userId).orElse(null);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> fields = new HashMap<>();
        try {
            fields = objectMapper.readValue(jsonDataFromRequest, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid json format");
        }

        UserDTO userDTO = new UserDTO();
        for (Map.Entry<String,String> item: fields.entrySet()) {
            if(item.getKey().equals("email")) userDTO.setEmail( item.getValue());
            if(item.getKey().equals("firstName")) userDTO.setFirstName(item.getValue());
            if(item.getKey().equals("lastName")) userDTO.setLastName( item.getValue());
            if(item.getKey().equals("birthday")) userDTO.setBirthday(item.getValue());
            if(item.getKey().equals("address")) userDTO.setAddress(item.getValue());
            if(item.getKey().equals("phone")) userDTO.setPhone(item.getValue());
        }
        userMapper.toUserFromSomeFields(userDTO,user);
        repository.save(user);
        return user;
    }
    public User updateUserById(Long id, UserDTO userDTO)  {
        User userInData = repository.findById(id).orElse(null);
        User userToData = userMapper.toUserFromAllFields(userDTO,userInData);
        repository.save(userToData);
        return userToData ;
    }
    public  void deleteUserById(Long id){
        repository.deleteById(id);

    }
    public List<User> searchByBirthdayFromTo(String  fromDateString, String toDateString){
        LocalDate  fromDate = validator.localDateValidator(fromDateString);
        LocalDate  toDate = validator.localDateValidator(toDateString);
        return repository.findUsersByBirthday(fromDate,toDate);
    }


}
