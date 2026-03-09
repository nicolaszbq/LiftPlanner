package com.nicolaszbq.ExerciseWorksheetManager.entities.imps;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("MEMBER")
@NoArgsConstructor
@SuperBuilder
public class UserMember extends User {

    public UserMember(String id, String username, String email, String password, Role role) {
        super(id,username,email,password,Role.TRAINER);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }




    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<WorksheetAssignment> assignments = new ArrayList<>();

    public void addAssignment(WorksheetAssignment wa){
        assignments.add(wa);
        wa.setMember(this);
    }

    public void removeAssignment(WorksheetAssignment wa){
        assignments.remove(wa);
        wa.setMember(null);
    }

}
