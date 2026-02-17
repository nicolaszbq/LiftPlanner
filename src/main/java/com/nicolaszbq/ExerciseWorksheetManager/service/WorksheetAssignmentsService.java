package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import com.nicolaszbq.ExerciseWorksheetManager.repository.WorksheetAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorksheetAssignmentsService {

    @Autowired
    private WorksheetAssignmentRepository worksheetAssignmentRepository;

    public List<WorksheetAssignment> findAllAssignments(){
        List<WorksheetAssignment> was = worksheetAssignmentRepository.findAll();
        return was;
    }

    public Optional<WorksheetAssignment> findAssignmentById(String id){
        Optional<WorksheetAssignment> ass = worksheetAssignmentRepository.findById(id);
        return ass;
    }

    public void deleteAssignmentById(String id){
        worksheetAssignmentRepository.deleteById(id);
    }
}
