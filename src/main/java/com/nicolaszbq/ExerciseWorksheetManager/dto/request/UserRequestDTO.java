package com.nicolaszbq.ExerciseWorksheetManager.dto.request;

import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    private String id;
    private String username;
    private String email;
    private String password;
    private Role role;
}
