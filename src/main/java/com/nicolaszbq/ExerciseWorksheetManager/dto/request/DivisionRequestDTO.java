package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionRequestDTO {
    private Type type;
    private String name;
}
