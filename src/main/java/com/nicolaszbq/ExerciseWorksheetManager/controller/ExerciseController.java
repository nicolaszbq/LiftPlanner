package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/getAllExercises")
    public List<Exercise> getAllExercises(){
        List<Exercise> ds = exerciseService.getAllExercises();
        return ds;
    }

    @PostMapping("/addExercise")
    public void addExercise(Exercise Exercise){
        exerciseService.addExercise(Exercise);
    }

    @GetMapping("/getExercise/{id}")
    public Optional<Exercise> getExerciseById(@PathVariable String id){
        Optional<Exercise> d = exerciseService.getExerciseById(id);
        return d;
    }

    @DeleteMapping("/deleteExercise/{id}")
    public void deleteExerciseById(@PathVariable String id){
        exerciseService.deleteExerciseById(id);
    }
}
