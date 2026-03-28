package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiRequestDTO {
    private List<GeminiMessageDTO> messages;
    private String trainerId;

}
