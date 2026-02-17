package com.nicolaszbq.ExerciseWorksheetManager.config;

import com.nicolaszbq.ExerciseWorksheetManager.entities.*;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserMember;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Status;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Type;
import com.nicolaszbq.ExerciseWorksheetManager.repository.*;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserTrainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private WorksheetAssignmentRepository worksheetAssignmentRepository;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("RUN EXECUTANDO");
//        Exercise e1 = new Exercise(null,"supino",15,3,"levanta o peso","youtube/supino");
//        Exercise e2 = new Exercise(null,"puxada",12,2,"puxa o peso","youtube/puxada");
//
//        List<Exercise> exercises = Arrays.asList(e1,e2);
//        Worksheet w = new Worksheet(null,"Julia_Stelmo_High_Volume");
//        Division d = new Division(null,Type.PERDAY,"A");
//        w.setDivisions(Arrays.asList(d));
//        e1.setDivision(d);
//        e2.setDivision(d);
//        d.setExercises(Arrays.asList(e1,e2));
//        d.setWorksheet(w);
//        UserTrainer t1 = new UserTrainer(null,"joao","joao@gmail.com","123asdg234",Role.TRAINER);
//        UserMember m1 = new UserMember(null,"silva","silva@gmail.com","1*_#h234",Role.MEMBER);
//
//        userRepository.saveAll(Arrays.asList(t1,m1));
//        worksheetRepository.save(w);
//        divisionRepository.save(d);
//        exerciseRepository.saveAll(Arrays.asList(e1,e2));
//        WorksheetAssignment wa = new WorksheetAssignment(null, new Date(),new Date(),Status.ACTIVE, t1, "dont", m1,w);
//
//
//        worksheetAssignmentRepository.save(wa);

    }
}
