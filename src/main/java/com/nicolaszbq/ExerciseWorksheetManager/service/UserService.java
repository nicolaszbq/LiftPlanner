package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.UserMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.UserRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.UserResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.User;
import com.nicolaszbq.ExerciseWorksheetManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapperDTO mapper;

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(mapper)
                .toList();
    }


    public UserResponseDTO create(UserRequestDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("This email was alredy used!");
        }
        User entity = mapper.toEntity(dto);
        User savedUser = userRepository.save(entity);
        return mapper.apply(savedUser);
    }



    public void deleteById(String id){
        userRepository.deleteById(id);
    }

    public Optional<UserResponseDTO> findUserById(String id){
        return userRepository.findById(id)
                .map(mapper);
    }

    public UserResponseDTO update(String id, UserRequestDTO dto){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return mapper.apply(savedUser);
    }

}
