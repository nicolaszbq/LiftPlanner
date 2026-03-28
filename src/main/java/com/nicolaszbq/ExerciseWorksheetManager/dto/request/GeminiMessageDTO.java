package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiMessageDTO {
    private String role;
    private String content;
    private String timestamp;
}
