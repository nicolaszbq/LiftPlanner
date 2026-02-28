package com.nicolaszbq.ExerciseWorksheetManager.entities.imps;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("TRAINER")
@NoArgsConstructor
@SuperBuilder
public class UserTrainer extends User {


    public UserTrainer(String id, String username, String email, String password, Role role) {
        super(id,username,email,password,Role.TRAINER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "assignedBy", cascade = CascadeType.ALL)
    private List<WorksheetAssignment> assignments = new ArrayList<>();



    public void addAssignment(WorksheetAssignment wa){
        assignments.add(wa);
        wa.setAssignedBy(this);
    }

    public void removeAssignment(WorksheetAssignment wa){
        assignments.remove(wa);
        wa.setAssignedBy(null);
    }
}
