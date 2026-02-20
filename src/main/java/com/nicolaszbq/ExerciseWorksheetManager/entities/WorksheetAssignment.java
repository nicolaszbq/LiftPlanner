package com.nicolaszbq.ExerciseWorksheetManager.entities;

import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserMember;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserTrainer;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "db_assignments")
@Getter
@Setter
@NoArgsConstructor
public class WorksheetAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Date assignedAt;
    private Date validUntil;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "usertrainer_id")
    private UserTrainer assignedBy;

    private String observations;


    @ManyToOne
    @JoinColumn(name = "usermember_id")
    private UserMember member;

    @ManyToOne
    @JoinColumn(name = "worksheet_id")
    private Worksheet worksheet;

    public WorksheetAssignment(String id,  Date validUntil, UserTrainer assignedBy, String observations, UserMember member, Worksheet worksheet) {
        this.id = id;
        this.assignedAt = new Date();
        this.validUntil = validUntil;
        this.status = Status.ACTIVE;
        this.assignedBy = assignedBy;
        this.observations = observations;
        this.member = member;
        this.worksheet = worksheet;
    }
}
