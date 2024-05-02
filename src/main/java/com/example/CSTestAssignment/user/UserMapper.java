package com.example.CSTestAssignment.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class UserMapper {
    @Autowired
    private ValidationUser validator;
    public User toUser(UserDTO userDTO){
        User user= new User();
        if(userDTO == null) throw new NullPointerException("userDTO is null");

        if (validator.userValidation(userDTO)){
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setBirthday(LocalDate.parse(userDTO.getBirthday()));
            user.setAddress(userDTO.getAddress());
            user.setPhone(userDTO.getPhone());
        }
        return user;

    }
    public User toUserFromAllFields(UserDTO userDTO,User user){
        if(userDTO==null || user==null) throw new NullPointerException("Some of params is null");
        if (validator.userValidation(userDTO)){
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setBirthday(LocalDate.parse(userDTO.getBirthday()));
            user.setAddress(userDTO.getAddress());
            user.setPhone(userDTO.getPhone());
        }
        return user;
    }
    public User toUserFromSomeFields(UserDTO userDTO,User user){
        if(userDTO==null||user==null) throw new NullPointerException("Some of params is null");
        String email = userDTO.getEmail();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String birthday = userDTO.getBirthday();
        String address = userDTO.getAddress();
        String phone = userDTO.getPhone();
        if(email  != null){
            validator.validationEmail(email);
            user.setEmail(email );
        }
        if(firstName !=null){
            validator.notEmptyName(firstName);
            user.setFirstName(firstName );
        }
        if(lastName != null){
            validator.notEmptySoname(lastName);
            user.setLastName(lastName);
        }
        if(birthday != null){
            validator.validationBirthday(birthday);
            user.setBirthday(LocalDate.parse(birthday));
        }
        if(address != null){
            user.setAddress(address);
        }
        if(phone != null){
            user.setPhone(phone);
        }
        return user;

    }

}
