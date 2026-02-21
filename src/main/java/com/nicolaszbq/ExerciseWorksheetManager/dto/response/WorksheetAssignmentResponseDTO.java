package com.nicolaszbq.ExerciseWorksheetManager.dto.response;

import com.nicolaszbq.ExerciseWorksheetManager.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorksheetAssignmentResponseDTO {

    private String id;
    private String trainerName;
    private String memberName;
    private String worksheetName;
    private Status status;
    private Date assignedAt;
    private String observations;
}
