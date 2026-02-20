package com.nicolaszbq.ExerciseWorksheetManager.dto.mapper;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.UserRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.UserResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserMember;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserTrainer;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapperDTO implements Function<User, UserResponseDTO> {

    //converts a user to a userresponsedto
    @Override
    public UserResponseDTO apply(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }


    //converts a userRequestDTO to a user entity
    public User toEntity(UserRequestDTO dto){
        if(dto.getRole() == Role.TRAINER){
            return new UserTrainer(
                    null,
                    dto.getUsername(),
                    dto.getEmail(),
                    dto.getPassword(),
                    dto.getRole()
            );
        } else if (dto.getRole() == Role.MEMBER) {
            return new UserMember(
                    null,
                    dto.getUsername(),
                    dto.getEmail(),
                    dto.getPassword(),
                    dto.getRole()
            );
        }
        throw new IllegalArgumentException("Invalid role!");
    }
}
