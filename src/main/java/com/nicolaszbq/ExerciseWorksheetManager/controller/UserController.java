package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.UserMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.UserRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.UserResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.repository.UserRepository;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapperDTO mapper;


    @GetMapping("/findAll")
    public List<UserResponseDTO> findAll(){
        return userService.findAll();
    }

    @PostMapping("/createUser")
    public void create(@RequestBody UserRequestDTO dto){
        userService.create(dto);
    }


    @GetMapping("/findById/{id}")
    public Optional<UserResponseDTO> findUserById(@PathVariable String id){
        return userRepository.findById(id)
                .map(mapper);
    }

    @PostMapping("/updateUser")
    public void update(String id, @RequestBody UserRequestDTO dto){
        userService.update(id,dto);
    }

}
