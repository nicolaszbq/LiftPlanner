package com.nicolaszbq.ExerciseWorksheetManager.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.WorksheetRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.service.WorksheetService;

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

    @GetMapping("/findByTrainerId/{trainerId}")
    public List<WorksheetResponseDTO> findWorksheetsByTrainerId(@PathVariable String trainerId){
        return worksheetService.findWorksheetsByTrainerId(trainerId);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('TRAINER')")
    public WorksheetResponseDTO update(@PathVariable String id,@RequestBody WorksheetRequestDTO dto){
        return worksheetService.update(id,dto);
    }
    
    @GetMapping("/findById/{id}")
    public Optional<WorksheetResponseDTO> findWorksheetById(@PathVariable String id){
        return worksheetService.findWorksheetById(id);
    }

    @GetMapping("/findByUser/{id}")
    public Optional<WorksheetResponseDTO> findWorksheetByUserId(@PathVariable String id){
        return worksheetService.getWorksheetByUserId(id);
    }

    @DeleteMapping("/deleteWorksheet/{id}")
    public void deleteWorksheetById(@PathVariable String id){
        worksheetService.deleteWorksheetById(id);
    }

    @PostMapping("/createWorksheet")
    public WorksheetResponseDTO createWorksheet(@RequestBody WorksheetRequestDTO dto){
        return worksheetService.createWorksheet(dto);
    }

    @PostMapping("/addDivision/{divisionId}/{worksheetId}")
    public void addDivision(@PathVariable String divisionId, @PathVariable String worksheetId){
        worksheetService.addDivision(divisionId,worksheetId);
    }
}
//exercise id: 8488acdb-c6cc-4aeb-a7a0-3ef39167459d
//division id: 93a9ff31-2afc-4c02-b887-9dd6328dba64