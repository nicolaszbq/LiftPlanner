package com.nicolaszbq.ExerciseWorksheetManager.repository;

import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, String> {
    List<Worksheet> getWorksheetByTrainerId(String trainerId);
}
