package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.DivisionMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.DivisionRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.ExerciseRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.DivisionResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.ExerciseResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.repository.DivisionRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DivisionMapperDTO mapper;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<DivisionResponseDTO> getAllDivisions(){
        return divisionRepository.findAll()
                .stream()
                .map(mapper)
                .toList();
    }


    public Optional<DivisionResponseDTO> getDivisionById(String id){
        return divisionRepository.findById(id)
                .map(mapper);
    }

    public void deleteDivisionById(String id){
        divisionRepository.deleteById(id);
    }

    public DivisionResponseDTO create(DivisionRequestDTO dto){
        Division div = new Division();
        Division saved = divisionRepository.save(div);
        return mapper.apply(saved);
    }

    public DivisionResponseDTO update(String id, DivisionRequestDTO dto){
        Division div = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Division not found"));
        div.setName(dto.getName());
        div.setType(dto.getType());
        Division saved = divisionRepository.save(div);
        return mapper.apply(saved);
    }

    public void linkExercise(String exerciseId, String divisionId){
        Division div = divisionRepository.findById(divisionId)
                .orElseThrow(() -> new RuntimeException("Division not found"));

        Exercise e1 = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        div.getExercises().add(e1);
        e1.setDivision(div);
        divisionRepository.save(div);
    }
}
