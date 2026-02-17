package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import com.nicolaszbq.ExerciseWorksheetManager.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

    public List<Division> getAllDivisions(){
        List<Division> ds = divisionRepository.findAll();
        return ds;
    }

    public void addDivision(Division division){
        divisionRepository.save(division);
    }

    public Optional<Division> getDivisionById(String id){
        Optional<Division> d = divisionRepository.findById(id);
        return d;
    }

    public void deleteDivisionById(String id){
        divisionRepository.deleteById(id);
    }
}
