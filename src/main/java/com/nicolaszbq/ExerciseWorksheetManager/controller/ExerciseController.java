package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.ExerciseRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.ExerciseResponseDTO;
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
    public List<ExerciseResponseDTO> getAllExercises(){
        List<ExerciseResponseDTO> ds = exerciseService.getAllExercises();
        return ds;
    }

    @GetMapping("/getExercise/{id}")
    public Optional<ExerciseResponseDTO> getExerciseById(@PathVariable String id){
        Optional<ExerciseResponseDTO> d = exerciseService.getExerciseById(id);
        return d;
    }

    @DeleteMapping("/deleteExercise/{id}")
    public void deleteExerciseById(@PathVariable String id){
        exerciseService.deleteExerciseById(id);
    }

    @PostMapping("/createExercise")
    public ExerciseResponseDTO create(@RequestBody ExerciseRequestDTO dto){
        return exerciseService.create(dto);
    }


    @PostMapping("/updateExercise/{id}")
    public void update(@PathVariable String id, ExerciseRequestDTO dto){
        exerciseService.update(id,dto);
    }
}
