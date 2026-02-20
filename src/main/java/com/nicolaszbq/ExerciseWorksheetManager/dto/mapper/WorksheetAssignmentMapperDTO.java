package com.nicolaszbq.ExerciseWorksheetManager.dto.mapper;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.WorksheetAssignmentRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetAssignmentResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserMember;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserTrainer;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Status;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Component
public class WorksheetAssignmentMapperDTO implements Function<WorksheetAssignment, WorksheetAssignmentResponseDTO> {


    @Override
    public WorksheetAssignmentResponseDTO apply(WorksheetAssignment worksheetAssignment) {
        return WorksheetAssignmentResponseDTO.builder()
                .id(worksheetAssignment.getId().toString())
                .trainerName(worksheetAssignment.getAssignedBy().getUsername())
                .memberName(worksheetAssignment.getMember().getUsername())
                .worksheetName(worksheetAssignment.getWorksheet().getName())
                .status(worksheetAssignment.getStatus())
                .assignedAt(worksheetAssignment.getAssignedAt())
                .build();
    }


    public WorksheetAssignment toEntity(
            WorksheetAssignmentRequestDTO dto,
            UserTrainer trainer,
            UserMember member,
            Worksheet worksheet
    ) {

        WorksheetAssignment wa = new WorksheetAssignment();

        wa.setAssignedAt(new Date());
        wa.setValidUntil(dto.getValidUntil());
        wa.setStatus(Status.ACTIVE);
        wa.setAssignedBy(trainer);
        wa.setObservations(dto.getObservations());
        wa.setMember(member);
        wa.setWorksheet(worksheet);

        return wa;
    }
}
