package org.example.gymroutine.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.entity.*;
import org.example.gymroutine.repository.ExerciseRepository;
import org.example.gymroutine.repository.RoutineRepository;
import org.example.gymroutine.repository.UserRepository;
import org.example.gymroutine.service.RoutineService;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.example.gymroutine.model.response.RoutineDTO;
import org.example.gymroutine.model.response.SetDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.example.gymroutine.mapper.RoutineMapper;
//...

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final RoutineMapper routineMapper;

    private UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<RoutineDTO> getAllRoutines() {
        return routineRepository.findAllByUser(getCurrentUser()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoutineDTO getRoutineById(String id) {
        RoutineEntity routine = routineRepository.findById(id).orElseThrow();
        if (!routine.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Access denied");
        }
        return mapToDTO(routine);
    }

    @Override
    @Transactional
    public RoutineDTO createRoutine(RoutineDTO request) {
        RoutineEntity routine = RoutineEntity.builder()
                .name(request.getName())
                .targetMuscleGroup(request.getTargetMuscleGroup())
                .user(getCurrentUser())
                .assignedDays(new ArrayList<>(request.getAssignedDays() != null ? request.getAssignedDays() : List.of()))
                .build();

        if (request.getExercises() != null) {
            for (int i = 0; i < request.getExercises().size(); i++) {
                ExerciseDTO exDto = request.getExercises().get(i);
                ExerciseEntity exercise = exerciseRepository.findById(exDto.getId()).orElseThrow();
                
                RoutineExerciseEntity re = RoutineExerciseEntity.builder()
                        .exercise(exercise)
                        .restTime(exDto.getRestTime())
                        .listOrder(i)
                        .build();
                
                if (exDto.getSets() != null) {
                    for (SetDTO setDto : exDto.getSets()) {
                        RoutineSetEntity rs = RoutineSetEntity.builder()
                                .reps(setDto.getReps())
                                .weight(setDto.getWeight())
                                .build();
                        re.addSet(rs);
                    }
                }
                routine.addExercise(re);
            }
        }

        routine = routineRepository.save(routine);
        return mapToDTO(routine);
    }

    @Override
    @Transactional
    public RoutineDTO updateRoutine(String id, RoutineDTO request) {
        RoutineEntity routine = routineRepository.findById(id).orElseThrow();
        if (!routine.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Access denied");
        }

        routine.setName(request.getName());
        routine.setTargetMuscleGroup(request.getTargetMuscleGroup());
        routine.getAssignedDays().clear();
        if (request.getAssignedDays() != null) {
            routine.getAssignedDays().addAll(request.getAssignedDays());
        }

        routine.getExercises().clear();
        if (request.getExercises() != null) {
            for (int i = 0; i < request.getExercises().size(); i++) {
                ExerciseDTO exDto = request.getExercises().get(i);
                ExerciseEntity exercise = exerciseRepository.findById(exDto.getId()).orElseThrow();
                
                RoutineExerciseEntity re = RoutineExerciseEntity.builder()
                        .exercise(exercise)
                        .restTime(exDto.getRestTime())
                        .listOrder(i)
                        .build();
                
                if (exDto.getSets() != null) {
                    for (SetDTO setDto : exDto.getSets()) {
                        RoutineSetEntity rs = RoutineSetEntity.builder()
                                .reps(setDto.getReps())
                                .weight(setDto.getWeight())
                                .build();
                        re.addSet(rs);
                    }
                }
                routine.addExercise(re);
            }
        }

        routine = routineRepository.save(routine);
        return mapToDTO(routine);
    }

    @Override
    public void deleteRoutine(String id) {
        RoutineEntity routine = routineRepository.findById(id).orElseThrow();
        if (!routine.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Access denied");
        }
        routineRepository.delete(routine);
    }

    private RoutineDTO mapToDTO(RoutineEntity entity) {
        return routineMapper.toDto(entity);
    }
}
