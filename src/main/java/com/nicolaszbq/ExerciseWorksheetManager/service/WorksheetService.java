package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.WorksheetMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.WorksheetRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.repository.DivisionRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.WorksheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorksheetService {

    @Autowired
    private WorksheetRepository worksheetRepository;

    @Autowired
    private WorksheetMapperDTO mapper;

    @Autowired
    private DivisionRepository divisionRepository;

    public List<WorksheetResponseDTO> findAllWorksheets(){
        return worksheetRepository.findAll()
                .stream()
                .map(mapper)
                .toList();
    }


    public Optional<WorksheetResponseDTO> findWorksheetById(String id){
        return worksheetRepository.findById(id)
                .map(mapper);
    }

    public void deleteWorksheetById(String id){
        worksheetRepository.deleteById(id);
    }

    public WorksheetResponseDTO create(WorksheetRequestDTO dto) {
        Worksheet entity = mapper.toEntity(dto);
        Worksheet saved = worksheetRepository.save(entity);
        return mapper.apply(saved);
    }

    public void addDivision(String divisionId, String worksheetId){
        Division div = divisionRepository.findById(divisionId)
                .orElseThrow(()-> new RuntimeException("No division was found!"));
        Worksheet work = worksheetRepository.findById(worksheetId)
                .orElseThrow(()-> new RuntimeException("No worksheet was found!"));

        work.getDivisions().add(div);
        worksheetRepository.save(work);
    }

}
