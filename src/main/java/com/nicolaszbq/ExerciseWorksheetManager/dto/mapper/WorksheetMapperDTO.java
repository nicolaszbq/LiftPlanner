package com.nicolaszbq.ExerciseWorksheetManager.dto.mapper;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.WorksheetRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetAssignmentResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class WorksheetMapperDTO implements Function<Worksheet, WorksheetResponseDTO> {


    private final DivisionMapperDTO divisionMapperDTO;

    public WorksheetMapperDTO(DivisionMapperDTO divisionMapperDTO) {
        this.divisionMapperDTO = divisionMapperDTO;
    }

    @Override
    public WorksheetResponseDTO apply(Worksheet worksheet) {
        return WorksheetResponseDTO.builder()
                .id(worksheet.getId())
                .name(worksheet.getName())
                .divisions(
                        worksheet.getDivisions()
                                .stream()
                                .map(divisionMapperDTO)
                                .toList()
                )
                .build();
    }

    public Worksheet toEntity(WorksheetRequestDTO dto){
        Worksheet w = new Worksheet();

        w.setName(dto.getName());

        return w;
    }
}
