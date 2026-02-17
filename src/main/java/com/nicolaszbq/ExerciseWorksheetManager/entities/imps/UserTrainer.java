package com.nicolaszbq.ExerciseWorksheetManager.entities.imps;

import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("TRAINER")
@NoArgsConstructor
public class UserTrainer extends User {


    public UserTrainer(String id, String username, String email, String password, Role role) {
        super(id,username,email,password,Role.TRAINER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


}
