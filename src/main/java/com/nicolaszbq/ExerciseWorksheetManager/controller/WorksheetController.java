package com.nicolaszbq.ExerciseWorksheetManager.controller;


import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worksheets")
public class WorksheetController {
    @Autowired
    private WorksheetService worksheetService;

    @GetMapping("/findAllWorksheets")
    public List<Worksheet> findAllWorksheets(){
        List<Worksheet> wos = worksheetService.findAllWorksheets();
        return wos;
    }

    @PostMapping("/addWorksheet")
    public void addWorksheet(@RequestBody Worksheet w){
        worksheetService.addWorksheet(w);
    }

    @DeleteMapping("/deleteWorksheet/{id}")
    public void deleteWorksheetById(@PathVariable String id){
        worksheetService.deleteWorksheetById(id);
    }
}
