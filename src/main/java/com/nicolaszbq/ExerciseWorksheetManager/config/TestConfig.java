package com.nicolaszbq.ExerciseWorksheetManager.config;

import com.nicolaszbq.ExerciseWorksheetManager.entities.Division;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Exercise;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Type;
import com.nicolaszbq.ExerciseWorksheetManager.repository.DivisionRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.ExerciseRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.UserRepository;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserTrainer;
import com.nicolaszbq.ExerciseWorksheetManager.repository.WorksheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorksheetRepository worksheetRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) throws Exception {

        Exercise e1 = new Exercise(null,"supino",15,3,"levanta o peso","youtube/supino");

        Exercise e2 = new Exercise(null,"puxada",12,2,"puxa o peso","youtube/puxada");

        List<Exercise> exercises = Arrays.asList(e1,e2);
        Worksheet w = new Worksheet(null,"Julia_Stelmo_High_Volume");
        Division d = new Division(null,Type.PERDAY,"A");
        w.setDivisions(Arrays.asList(d));
        e1.setDivision(d);
        e2.setDivision(d);
        d.setExercises(Arrays.asList(e1,e2));
        d.setWorksheet(w);

        worksheetRepository.save(w);
        divisionRepository.save(d);
        exerciseRepository.save(e1);
        exerciseRepository.save(e2);


    }
}
