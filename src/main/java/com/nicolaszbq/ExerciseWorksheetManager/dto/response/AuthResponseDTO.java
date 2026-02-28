package com.nicolaszbq.ExerciseWorksheetManager.dto.response;

import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponseDTO {
    private String id;
    private String name;
    private String email;
    private Role role;
}
