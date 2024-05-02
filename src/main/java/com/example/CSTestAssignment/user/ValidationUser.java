package com.example.CSTestAssignment.user;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationUser {

    @Value("${user.age.limit}")
    private int ageLimit;
    public boolean validationEmail(String email){
        if(email ==null){
            throw new NullPointerException("Field 'email' is required");
        }
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()){
            throw new IllegalArgumentException("Invalid email");
        }
        return true;
    }
    public boolean validationBirthday(String birthday) {
        if(birthday ==null) {
            throw new NullPointerException("Field 'birthday' is required");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate dateFormat = LocalDate.parse(birthday, formatter);
            if (dateFormat.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("You input incorrect birthday");
            }

            int ageUser = Period.between(dateFormat,LocalDate.now()).getYears();
            if(ageUser<ageLimit){
                throw new IllegalArgumentException("you are younger than "+ageLimit);
            }
            return true;
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format");

        }
    }

    public boolean notEmptyName(String firstName){
        if(firstName ==null) {
            throw new NullPointerException("Field 'firstName' is required");
        }
        if(firstName.isBlank()){
            throw new IllegalArgumentException("You don't input your first name");
        }
        return true;
    }
    public boolean notEmptySoname (String lastName){
        if(lastName ==null) {
            throw new NullPointerException("Field 'lastName' is required");
        }
        if(lastName.length()<1) {
            throw new IllegalArgumentException("You don't input your  last name");
        }
        return true;
    }

    public boolean userValidation (UserDTO userDTO) throws IllegalArgumentException{
        String email = userDTO.getEmail();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String birthday = userDTO.getBirthday();
        if (!validationEmail(email)) return false;
        if (!notEmptyName(firstName)) return false;
        if(!notEmptySoname(lastName)) return false;
        if (!validationBirthday(birthday))return false;
        return true;
    }
    public LocalDate localDateValidator (String date){
        if(date==null){
            throw new NullPointerException("You don't input date");
        }
        LocalDate parse;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            parse= LocalDate.parse(date, formatter);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format");

        }
        return parse;
    }

}
