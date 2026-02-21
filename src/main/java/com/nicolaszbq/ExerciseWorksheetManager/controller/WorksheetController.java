package com.nicolaszbq.ExerciseWorksheetManager.controller;


import com.nicolaszbq.ExerciseWorksheetManager.dto.request.WorksheetRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.service.WorksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/worksheets")
public class WorksheetController {
    @Autowired
    private WorksheetService worksheetService;

    @GetMapping("/findAllWorksheets")
    public List<WorksheetResponseDTO> findAllWorksheets(){
        List<WorksheetResponseDTO> wos = worksheetService.findAllWorksheets();
        return wos;
    }

    @GetMapping("/findById/{id}")
    public Optional<WorksheetResponseDTO> findWorksheetById(@PathVariable String id){
        return worksheetService.findWorksheetById(id);
    }

    @DeleteMapping("/deleteWorksheet/{id}")
    public void deleteWorksheetById(@PathVariable String id){
        worksheetService.deleteWorksheetById(id);
    }

    @PostMapping("/createWorksheet")
    public WorksheetResponseDTO createWorksheet(@RequestBody WorksheetRequestDTO dto){
        return worksheetService.create(dto);
    }

    @PostMapping("/addDivision/{divisionId}/{worksheetId}")
    public void addDivision(@PathVariable String divisionId, @PathVariable String worksheetId){
        worksheetService.addDivision(divisionId,worksheetId);
    }
}
//exercise id: 8488acdb-c6cc-4aeb-a7a0-3ef39167459d
//division id: 93a9ff31-2afc-4c02-b887-9dd6328dba64