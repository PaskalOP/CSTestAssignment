package com.example.CSTestAssignment.services;

import com.example.CSTestAssignment.repository.UserDTO;
import com.example.CSTestAssignment.services.utils.ValidationUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class ValidationUserTest {
    @Value("${user.age.limit}")
    private int ageLimit;
    @Autowired
    private ValidationUser validationUser;

    @Test
    void validationEmail_validEmail_shouldReturnTrue() {
        assertTrue(validationUser.validationEmail("test@example.com"));
    }
    @Test
    void whenCalled_validationEmail_thenIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationUser.validationEmail("testexample.com");
            validationUser.validationEmail("testexample");
        });
        assertEquals("Invalid email", exception.getMessage());
    }
    @Test
    void whenCalled_validationEmail_thenNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            validationUser.validationEmail(null);
        });
        assertEquals("Field 'email' is required", exception.getMessage());
    }

    @Test
    void whenCalled_validationBirthday_shouldReturnTrue() {
        assertTrue(validationUser.validationBirthday("1999-11-11"));
    }
    @Test
    void whenCalled_validationBirthday_thenNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            validationUser.validationBirthday(null);
        });
        assertEquals("Field 'birthday' is required", exception.getMessage());
    }
    @Test
    void whenCalled_validationBirthday_thenIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationUser.validationBirthday("null");
            validationUser.validationBirthday("20");
            validationUser.validationBirthday("2021.01.04");
            validationUser.validationBirthday("2021/01/04");
            validationUser.validationBirthday("01/04/2002");
        });
        assertEquals("Invalid date format", exception.getMessage());
    }
    @Test
    void whenCalled_validationBirthday_thenIllegalArgumentException_incorrect() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationUser.validationBirthday(LocalDate.now().plusDays(1).toString());

        });
        assertEquals("You input incorrect birthday", exception.getMessage());
    }
    @Test
    void whenCalled_validationBirthday_thenIllegalArgumentException_toYoung() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationUser.validationBirthday(LocalDate.now()
                    .minusYears(ageLimit)
                    .plusDays(1)
                    .toString());

        });
        assertEquals("you are younger than "+ageLimit, exception.getMessage());
    }

    @Test
    void whenCalled_notEmpty_thenNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            validationUser.notEmptyName(null);
        });
        assertEquals("Field 'firstName' is required", exception.getMessage());
    }
    @Test
    void whenCalled_notEmptyName_thenIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationUser.notEmptyName("");
        });
        assertEquals("You don't input your first name", exception.getMessage());
    }

    @Test
    void whenCalled_notEmptySoname_thenNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            validationUser.notEmptySoname(null);
        });
        assertEquals("Field 'lastName' is required", exception.getMessage());
    }
    @Test
    void whenCalled_notEmptySoname_thenIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows( IllegalArgumentException.class, () -> {
            validationUser.notEmptySoname("");
        });
        assertEquals("You don't input your  last name", exception.getMessage());
    }
    @Test
    void whenCalled_notEmptySoname_shouldReturnTrue() {
        assertTrue(validationUser.notEmptySoname("test"));
    }
    @Test
    void whenCalled_notEmptyName_shouldReturnTrue() {
        assertTrue(validationUser.notEmptyName("test"));
    }

    @Test
    void whenCalled_userValidation_shouldReturnTrue() {
        UserDTO userDTO = new UserDTO("test@example.com","Jon","Snow","2001-02-03","Kiev","234");
        assertTrue(validationUser.userValidation(userDTO));
    }


    @Test
    void whenCalled_localDateValidator_inputCorrectly() {
        String date = "2003-11-11";
        LocalDate result = validationUser.localDateValidator(date);
        assertEquals(LocalDate.of(2003,11,11),result);
    }
    @Test
    void whenCalled_localDateValidator_thenNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            validationUser.localDateValidator(null);
        });
        assertEquals("You don't input date", exception.getMessage());
    }
    @Test
    void whenCalled_localDateValidator_thenIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows( IllegalArgumentException.class, () -> {
            validationUser.localDateValidator("2022/04/30");
            validationUser.localDateValidator("");
            validationUser.localDateValidator("2022.04.30");
        });
        assertEquals("Invalid date format", exception.getMessage());

    }
}