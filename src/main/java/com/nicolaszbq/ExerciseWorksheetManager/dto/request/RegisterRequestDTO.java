package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    private String name;
    private String email;
    private String password;
    private Role role;
}
