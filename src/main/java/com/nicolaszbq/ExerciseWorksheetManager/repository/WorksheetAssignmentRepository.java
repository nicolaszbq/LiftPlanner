package com.nicolaszbq.ExerciseWorksheetManager.repository;

import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorksheetAssignmentRepository extends JpaRepository<WorksheetAssignment, String> {
}
