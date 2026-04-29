package org.example.gymroutine.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.entity.ExerciseEntity;
import org.example.gymroutine.repository.ExerciseRepository;
import org.example.gymroutine.service.ExerciseService;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream().map(e -> 
            ExerciseDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .muscleGroup(e.getMuscleGroup())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public ExerciseDTO createExercise(ExerciseDTO request) {
        ExerciseEntity exercise = ExerciseEntity.builder()
                .name(request.getName())
                .muscleGroup(request.getMuscleGroup())
                .build();
        exercise = exerciseRepository.save(exercise);
        
        return ExerciseDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .muscleGroup(exercise.getMuscleGroup())
                .build();
    }
}
