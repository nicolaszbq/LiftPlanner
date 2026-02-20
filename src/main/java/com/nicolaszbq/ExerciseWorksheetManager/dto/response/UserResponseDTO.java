package com.nicolaszbq.ExerciseWorksheetManager.dto.response;

import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDTO {
    private String id;
    private String username;
    private Role role;
}
