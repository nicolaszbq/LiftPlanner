package com.nicolaszbq.ExerciseWorksheetManager.repository;

import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, String> {
    List<Worksheet> getWorksheetByTrainerId(String trainerId);
    Optional<Worksheet> getWorksheetByUserId(String userid);

}
