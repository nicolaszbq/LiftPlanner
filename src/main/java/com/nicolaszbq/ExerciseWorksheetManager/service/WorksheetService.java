package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.WorksheetMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.DivisionRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.ExerciseRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.WorksheetRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.repository.DivisionRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.UserRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.WorksheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class WorksheetService {

    @Autowired
    private UserRepository userRepository;

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

    public WorksheetResponseDTO createWorksheet(WorksheetRequestDTO dto) {
        System.out.println("Chegou no service");
        Worksheet worksheet = new Worksheet();
        worksheet.setName(dto.getName());

        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        User trainer = userRepository.findById(dto.getTrainerId()).orElseThrow();

        worksheet.setUser(user);
        worksheet.setTrainer(trainer);

        List<Division> divisions = new ArrayList<>();

        for (DivisionRequestDTO divisionDTO : dto.getDivisions()) {

            Division division = new Division();
            division.setName(divisionDTO.getName());
            division.setWorksheet(worksheet);

            List<Exercise> exercises = new ArrayList<>();

            for (ExerciseRequestDTO exerciseDTO : divisionDTO.getExercises()) {

                Exercise exercise = new Exercise();
                exercise.setName(exerciseDTO.getName());
                exercise.setReps(exerciseDTO.getReps());
                exercise.setSeries(exerciseDTO.getSeries());
                exercise.setDescription(exerciseDTO.getDescription());
                exercise.setVideoUrl(exerciseDTO.getVideoUrl());
                exercise.setDivision(division);

                exercises.add(exercise);
            }

            division.setExercises(exercises);
            divisions.add(division);
        }

        worksheet.setDivisions(divisions);

        worksheetRepository.save(worksheet);
        return mapper.apply(worksheet);

    }

    public void addDivision(String divisionId, String worksheetId){
        Division div = divisionRepository.findById(divisionId)
                .orElseThrow(()-> new RuntimeException("No division was found!"));
        Worksheet work = worksheetRepository.findById(worksheetId)
                .orElseThrow(()-> new RuntimeException("No worksheet was found!"));

        work.getDivisions().add(div);
        worksheetRepository.save(work);
    }

    public Worksheet saveWorksheet(Worksheet worksheet){
        for(Division div : worksheet.getDivisions()){
            div.setWorksheet(worksheet);
            for (Exercise ex : div.getExercises()){
                ex.setDivision(div);
            }
        }
        return worksheetRepository.save(worksheet);
    }

}
