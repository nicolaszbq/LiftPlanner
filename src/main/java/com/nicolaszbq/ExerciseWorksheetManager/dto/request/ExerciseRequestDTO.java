package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseRequestDTO {
    private String name;
    private Integer reps;
    private Integer series;
    private String description;
    private String videoUrl;
}
