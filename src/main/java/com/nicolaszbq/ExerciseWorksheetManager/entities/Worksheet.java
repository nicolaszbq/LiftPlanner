package com.nicolaszbq.ExerciseWorksheetManager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "db_worksheet")
@Getter
@Setter
@NoArgsConstructor
public class Worksheet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "worksheet", cascade = CascadeType.ALL)
    private List<Division> divisions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="trainer_id")
    private User trainer;

    @JsonIgnore
    @OneToMany(mappedBy = "worksheet")
    private List<WorksheetAssignment> assignments = new ArrayList<>();


    public Worksheet(String id, String name){
        this.id = id;
        this.name = name;
    }

    public void addDivision(Division d){
        divisions.add(d);
        d.setWorksheet(this);
    }

    public void removeDivision(Division d){
        divisions.remove(d);
        d.setWorksheet(null);
    }

    public void addAssignment(WorksheetAssignment wa){
        assignments.add(wa);
        wa.setWorksheet(this);
    }

    public void removeAssignment(WorksheetAssignment wa){
        assignments.remove(wa);
        wa.setWorksheet(null);
    }

}
