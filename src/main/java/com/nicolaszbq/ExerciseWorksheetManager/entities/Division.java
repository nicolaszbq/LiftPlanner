package com.nicolaszbq.ExerciseWorksheetManager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "db_divisions")
@Getter
@Setter
@NoArgsConstructor
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Type type;
    private String name;

    @ManyToOne
    @JoinColumn(name = "worksheet_id")
    private Worksheet worksheet;

    @JsonIgnore
    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();

    public Division(String id, Type type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public void addExercise(Exercise e){
        exercises.add(e);
        e.setDivision(this);
    }

    public void removeExercise(Exercise e){
        exercises.remove(e);
        e.setDivision(null);
    }
}
