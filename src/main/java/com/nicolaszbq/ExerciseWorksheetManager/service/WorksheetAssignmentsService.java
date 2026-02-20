package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.nicolaszbq.ExerciseWorksheetManager.dto.mapper.WorksheetAssignmentMapperDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.request.WorksheetAssignmentRequestDTO;
import com.nicolaszbq.ExerciseWorksheetManager.dto.response.WorksheetAssignmentResponseDTO;
import com.nicolaszbq.ExerciseWorksheetManager.entities.Worksheet;
import com.nicolaszbq.ExerciseWorksheetManager.entities.WorksheetAssignment;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserMember;
import com.nicolaszbq.ExerciseWorksheetManager.entities.imps.UserTrainer;
import com.nicolaszbq.ExerciseWorksheetManager.repository.UserRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.WorksheetAssignmentRepository;
import com.nicolaszbq.ExerciseWorksheetManager.repository.WorksheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorksheetAssignmentsService {

    @Autowired
    private WorksheetAssignmentRepository worksheetAssignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorksheetRepository worksheetRepository;

    @Autowired
    private WorksheetAssignmentMapperDTO mapper;




    public List<WorksheetAssignmentResponseDTO> findAllAssignments(){
        return worksheetAssignmentRepository.findAll()
                .stream()
                .map(mapper)
                .toList();
    }

    public Optional<WorksheetAssignment> findAssignmentById(String id){
        Optional<WorksheetAssignment> ass = worksheetAssignmentRepository.findById(id);
        return ass;
    }

    public void deleteAssignmentById(String id){
        worksheetAssignmentRepository.deleteById(id);
    }

    public WorksheetAssignmentResponseDTO create(WorksheetAssignmentRequestDTO dto) {

        //Buscar Trainer
        UserTrainer trainer = (UserTrainer) userRepository.findById(dto.getTrainerId())
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        //Buscar Member
        UserMember member = (UserMember) userRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        //Buscar Worksheet
        Worksheet worksheet = worksheetRepository.findById(dto.getWorksheetId())
                .orElseThrow(() -> new RuntimeException("Worksheet not found"));

        //Converter DTO → Entity
        WorksheetAssignment entity =
                mapper.toEntity(dto, trainer, member, worksheet);

        //Salvar no banco
        worksheetAssignmentRepository.save(entity);

        // Converter Entity → ResponseDTO
        return mapper.apply(entity);
    }
}
