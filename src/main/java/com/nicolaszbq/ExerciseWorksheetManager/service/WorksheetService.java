package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.repository.WorksheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorksheetService {

    @Autowired
    private WorksheetRepository worksheetRepository;

    public List<Worksheet> findAllWorksheets(){
        List<Worksheet> wos = worksheetRepository.findAll();
        return wos;
    }

    public void deleteWorksheetById(String id){
        worksheetRepository.deleteById(id);
    }

    public void addWorksheet(Worksheet w){
        worksheetRepository.save(w);
    }
}
