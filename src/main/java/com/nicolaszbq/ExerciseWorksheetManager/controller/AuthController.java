package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.LoginRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.RegisterRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.AuthResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO dto){
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO dto){
        return authService.login(dto);
    }
}
