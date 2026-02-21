package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.ExerciseMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.ExerciseRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.UserRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.ExerciseResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseMapperDTO mapper;

    public List<ExerciseResponseDTO> getAllExercises(){
        return exerciseRepository.findAll()
                .stream()
                .map(mapper)
                .toList();
    }


    public Optional<ExerciseResponseDTO> getExerciseById(String id){
        return exerciseRepository.findById(id)
                .map(mapper);
    }


    public ExerciseResponseDTO create(ExerciseRequestDTO dto){
        Exercise entity = mapper.toEntity(dto);
        Exercise saved = exerciseRepository.save(entity);
        return mapper.apply(saved);

    }


    public ExerciseResponseDTO update(String id, ExerciseRequestDTO dto){
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        exercise.setName(dto.getName());
        exercise.setVideoUrl(dto.getVideoUrl());
        exercise.setSeries(dto.getSeries());
        exercise.setDescription(dto.getDescription());
        exercise.setReps(dto.getReps());
        Exercise saved = exerciseRepository.save(exercise);
        return mapper.apply(saved);
    }

    public void deleteExerciseById(String id){
        exerciseRepository.deleteById(id);
    }
}
