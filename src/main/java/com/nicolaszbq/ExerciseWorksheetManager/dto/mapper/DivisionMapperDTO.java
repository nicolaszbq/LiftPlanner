package com.nicolaszbq.ExerciseWorksheetManager.dto.mapper;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.DivisionRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.DivisionResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DivisionMapperDTO implements Function<Division, DivisionResponseDTO> {

    @Autowired
    private final ExerciseMapperDTO exerciseMapperDTO;

    public DivisionMapperDTO(ExerciseMapperDTO exerciseMapperDTO) {
        this.exerciseMapperDTO = exerciseMapperDTO;
    }

    @Override
    public DivisionResponseDTO apply(Division division) {
        return DivisionResponseDTO.builder()
                .id(division.getId())
                .name(division.getName())
                .type(division.getType())
                .exercises(
                        division.getExercises()
                                .stream()
                                .map(exerciseMapperDTO)
                                .toList()
                )
                .build();
    }

    public Division toEntity(DivisionRequestDTO dto){
        Division div = new Division();
        div.setName(dto.getName());
        div.setType(dto.getType());
        return div;
    }
}
