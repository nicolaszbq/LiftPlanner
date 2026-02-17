package com.nicolaszbq.ExerciseWorksheetManager.entities.imps;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue("MEMBER")
@NoArgsConstructor
public class UserMember extends User {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    public UserMember(String id, String username, String email, String password, Role role){
        super(id,username,email,password,Role.MEMBER);
    }

    @OneToMany(mappedBy = "member")
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
