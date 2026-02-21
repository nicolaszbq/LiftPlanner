package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.DivisionRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.DivisionResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import com.nicolaszbq.ExerciseWorksheetManager.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/divisions")
public class DivisionController {

    @Autowired
    private DivisionService divisionService;

    @GetMapping("/getAllDivisions")
    public List<DivisionResponseDTO> getAllDivisions(){
        List<DivisionResponseDTO> ds = divisionService.getAllDivisions();
        return ds;
    }

    @GetMapping("/getDivision")
    public Optional<DivisionResponseDTO> getDivisionById(String id){
        Optional<DivisionResponseDTO> d = divisionService.getDivisionById(id);
        return d;
    }

    @DeleteMapping("/deleteDivision/{id}")
    public void deleteDivisionById(@PathVariable String id){
        divisionService.deleteDivisionById(id);
    }

    @PostMapping("/create")
    public DivisionResponseDTO create(@RequestBody DivisionRequestDTO dto){
        return divisionService.create(dto);
    }

    @PostMapping("/update/{id}")
    public DivisionResponseDTO update(@PathVariable String id, @RequestBody DivisionRequestDTO dto){
        return divisionService.update(id, dto);
    }

    @PostMapping("/addExercise/{exerciseId}/{divisionId}")
    public void addExercise(@PathVariable String exerciseId, @PathVariable String divisionId ){
        divisionService.linkExercise(exerciseId,divisionId);
    }
}
