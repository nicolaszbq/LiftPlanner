package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.dto.request.LoginRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.RegisterRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.AuthResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserMember;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserTrainer;
import com.nicolaszbq.ExerciseWorksheetManager.enums.Role;
import com.nicolaszbq.ExerciseWorksheetManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //creates user and saves it to the database
    public AuthResponseDTO register(RegisterRequestDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Email alredy in use!");
        }
        if(dto.getRole() == Role.ADMIN || dto.getRole() == null ){
            throw new RuntimeException("Invalid Role!");
        }
        User u;
        if(dto.getRole() == Role.MEMBER){
            u = UserMember.builder()
                    .username(dto.getName())
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(dto.getRole())
                    .build();
        }else {
            u = UserTrainer.builder()
                    .username(dto.getName())
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(dto.getRole())
                    .build();
        }

        User saved = userRepository.save(u);
        return AuthResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getUsername())
                .email(saved.getEmail())
                .role(saved.getRole())
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO dto){

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        return AuthResponseDTO.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
