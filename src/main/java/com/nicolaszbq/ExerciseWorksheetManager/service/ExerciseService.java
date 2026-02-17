package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises(){
        List<Exercise> ds = exerciseRepository.findAll();
        return ds;
    }

    public void addExercise(Exercise Exercise){
        exerciseRepository.save(Exercise);
    }

    public Optional<Exercise> getExerciseById(String id){
        Optional<Exercise> d = exerciseRepository.findById(id);
        return d;
    }

    public void deleteExerciseById(String id){
        exerciseRepository.deleteById(id);
    }
}
