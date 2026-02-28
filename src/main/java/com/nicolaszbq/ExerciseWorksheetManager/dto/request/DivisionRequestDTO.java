package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import com.nicolaszbq.ExerciseWorksheetManager.enums.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionRequestDTO {
    private Type type;
    private String name;
    private List<ExerciseRequestDTO> exercises;
}
