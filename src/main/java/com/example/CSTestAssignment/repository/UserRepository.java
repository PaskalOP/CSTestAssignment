package com.example.CSTestAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.birthday BETWEEN :fromDate AND :toDate")
    List<User> findUsersByBirthday(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
