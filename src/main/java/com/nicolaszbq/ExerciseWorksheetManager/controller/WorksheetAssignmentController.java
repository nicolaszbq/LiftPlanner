package com.nicolaszbq.ExerciseWorksheetManager.controller;


import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import com.nicolaszbq.ExerciseWorksheetManager.service.WorksheetAssignmentsService;
import com.nicolaszbq.ExerciseWorksheetManager.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assignments")
public class WorksheetAssignmentController {

    @Autowired
    private WorksheetAssignmentsService worksheetAssignmentsService;
    @GetMapping("/findAllAssignments")
    public List<WorksheetAssignment> findAllAssignments(){
        List<WorksheetAssignment> was = worksheetAssignmentsService.findAllAssignments();
        return was;
    }

    @GetMapping("/findAssignment/{id}")
    public Optional<WorksheetAssignment> getAssignmentById(@PathVariable String id){
        Optional<WorksheetAssignment> wa = worksheetAssignmentsService.findAssignmentById(id);
        return wa;
    }

    @DeleteMapping("/deleteAssignment/{id}")
    public void deleteAssignmentById(@PathVariable String id){
        worksheetAssignmentsService.deleteAssignmentById(id);
    }
}
