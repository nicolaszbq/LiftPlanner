package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorksheetAssignmentRequestDTO {
    private String trainerId;
    private String memberId;
    private String worksheetId;
    private Date validUntil;
    private String observations;
}
