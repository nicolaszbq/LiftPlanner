package com.nicolaszbq.ExerciseWorksheetManager.controller;

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
    public List<Division> getAllDivisions(){
        List<Division> ds = divisionService.getAllDivisions();
        return ds;
    }

    @PostMapping("/addDivision")
    public void addDivision(Division division){
        divisionService.addDivision(division);
    }

    @GetMapping("/getDivision")
    public Optional<Division> getDivisionById(String id){
        Optional<Division> d = divisionService.getDivisionById(id);
        return d;
    }

    @DeleteMapping("/deleteDivision/{id}")
    public void deleteDivisionById(@PathVariable String id){
        divisionService.deleteDivisionById(id);
    }
}
