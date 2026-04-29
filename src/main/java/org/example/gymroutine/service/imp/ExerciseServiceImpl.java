package org.example.gymroutine.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.entity.ExerciseEntity;
import org.example.gymroutine.repository.ExerciseRepository;
import org.example.gymroutine.service.ExerciseService;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.gymroutine.mapper.ExerciseMapper;
//...

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public List<ExerciseDTO> getAllExercises() {
        return exerciseMapper.toDtoList(exerciseRepository.findAll());
    }

    @Override
    public ExerciseDTO createExercise(ExerciseDTO request) {
        ExerciseEntity exercise = exerciseMapper.toEntity(request);
        exercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exercise);
    }
}
