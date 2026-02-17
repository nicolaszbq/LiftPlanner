package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainers")
public class UserTrainerController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/deleteMemberById/{id}")
    public void deleteById(@PathVariable String id){
        userService.deleteById(id);
    }
}
