package com.nicolaszbq.ExerciseWorksheetManager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "db_exercises")
@Getter
@Setter
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    private String name;
    private Integer reps;
    private Integer series;
    private String description;
    private String videoUrl;

    public Exercise(String id, String name, Integer reps, Integer series, String description, String videoUrl) {
        this.id = id;
        this.name = name;
        this.reps = reps;
        this.series = series;
        this.description = description;
        this.videoUrl = videoUrl;
    }
}
