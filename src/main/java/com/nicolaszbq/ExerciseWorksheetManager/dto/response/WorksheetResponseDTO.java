package com.nicolaszbq.ExerciseWorksheetManager.dto.response;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorksheetResponseDTO {
    private String id;
    private String name;
    private List<DivisionResponseDTO> divisions;
}
