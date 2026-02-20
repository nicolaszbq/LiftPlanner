package com.nicolaszbq.ExerciseWorksheetManager.repository;

import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
