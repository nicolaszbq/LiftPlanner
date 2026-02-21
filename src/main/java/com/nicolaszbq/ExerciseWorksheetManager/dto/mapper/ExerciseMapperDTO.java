package com.nicolaszbq.ExerciseWorksheetManager.dto.mapper;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.ExerciseRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.ExerciseResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExerciseMapperDTO implements Function<Exercise, ExerciseResponseDTO> {


    @Override
    public ExerciseResponseDTO apply(Exercise e) {
        return ExerciseResponseDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .reps(e.getReps())
                .series(e.getSeries())
                .description(e.getDescription())
                .videoUrl(e.getVideoUrl())
                .build();
    }

    public Exercise toEntity(ExerciseRequestDTO dto){
        Exercise e = new Exercise();

        e.setName(dto.getName());
        e.setReps(dto.getReps());
        e.setSeries(dto.getSeries());
        e.setDescription(dto.getDescription());
        e.setVideoUrl(dto.getVideoUrl());

        return e;
    }
}
