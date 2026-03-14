package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.UserMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.UserRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.UserResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.repository.UserRepository;
import com.nicolaszbq.ExerciseWorksheetManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping("/getMembers")
    public List<UserResponseDTO> getMembers(@RequestParam(required=false) String q){
        return userService.findMembers(q);
    }

    @PostMapping("/uploadPhoto/{id}")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file) throws IOException {

        String filename = id + "_" + file.getOriginalFilename();

        //define a pasta a qual a foto sera salva
        Path savePath = Paths.get("uploads/" + filename);
        Files.createDirectories(savePath.getParent()); // cria a pasta se não existir
        Files.write(savePath, file.getBytes());

        // cria a url da foto do usuario
        String photoUrl = "/uploads/" + filename;

        userService.updatePhotoUrl(id, photoUrl);

        return ResponseEntity.ok(photoUrl);
    }


    @GetMapping("/getByEmail/{email}")
    public Optional<UserResponseDTO> getUserByEmail(@PathVariable String email){
        return userService.findUserByEmail(email);
    }

    @GetMapping("/getId/{email}")
    public String getIdByEmail(@PathVariable String email){
        return userService.getIdByEmail(email);
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
