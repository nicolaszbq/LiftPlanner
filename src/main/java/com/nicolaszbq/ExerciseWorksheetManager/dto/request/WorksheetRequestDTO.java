package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class WorksheetRequestDTO {
    private String name;
    private String userId;
    private String trainerId;
    private List<DivisionRequestDTO> divisions;
}
