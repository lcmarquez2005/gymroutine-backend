package org.example.gymroutine.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.entity.*;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.example.gymroutine.model.response.SetDTO;
import org.example.gymroutine.model.response.WorkoutSessionDTO;
import org.example.gymroutine.repository.UserRepository;
import org.example.gymroutine.repository.WorkoutSessionRepository;
import org.example.gymroutine.service.WorkoutService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserRepository userRepository;

    private UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public WorkoutSessionDTO createWorkoutSession(WorkoutSessionDTO request) {
        // --- Recálculo de métricas en el servidor (no confiamos en el cliente) ---
        int totalSets = 0;
        int completedSets = 0;
        float totalVolume = 0f;

        if (request.getExercises() != null) {
            for (ExerciseDTO exercise : request.getExercises()) {
                if (exercise.getSets() != null) {
                    for (SetDTO set : exercise.getSets()) {
                        totalSets++;
                        if (Boolean.TRUE.equals(set.getCompleted())) {
                            completedSets++;
                            if (!"TIME".equalsIgnoreCase(set.getSetType())) {
                                float reps = set.getReps() != null ? set.getReps() : 0;
                                float weight = set.getWeight() != null ? set.getWeight() : 0;
                                totalVolume += reps * weight;
                            }
                        }
                    }
                }
            }
        }

        // --- Construcción de la sesión ---
        WorkoutSessionEntity session = WorkoutSessionEntity.builder()
                .user(getCurrentUser())
                .routineName(request.getRoutineName())
                .date(request.getDate() != null ? Instant.parse(request.getDate()) : Instant.now())
                .durationMinutes(request.getDurationMinutes())
                .totalVolume(totalVolume)
                .completedSets(completedSets)
                .totalSets(totalSets)
                .muscleGroupsTrained(new ArrayList<>(
                        request.getMuscleGroupsTrained() != null ? request.getMuscleGroupsTrained() : List.of()
                ))
                .build();

        // --- Ejercicios y Sets ---
        if (request.getExercises() != null) {
            for (ExerciseDTO exDto : request.getExercises()) {
                WorkoutExerciseEntity workoutExercise = WorkoutExerciseEntity.builder()
                        .name(exDto.getName())
                        .muscleGroup(exDto.getMuscleGroup())
                        .jointPain(exDto.getJointPain() != null ? exDto.getJointPain() : false)
                        .possibleInjury(exDto.getPossibleInjury() != null ? exDto.getPossibleInjury() : false)
                        .feelingSick(exDto.getFeelingSick() != null ? exDto.getFeelingSick() : false)
                        .build();

                if (exDto.getSets() != null) {
                    for (SetDTO setDto : exDto.getSets()) {
                        WorkoutSetEntity workoutSet = WorkoutSetEntity.builder()
                                .setType(setDto.getSetType())
                                .targetRepRange(setDto.getTargetRepRange())
                                .targetWeight(setDto.getTargetWeight())
                                .targetTimeSeconds(setDto.getTargetTimeSeconds())
                                .reps(setDto.getReps())
                                .weight(setDto.getWeight())
                                .completed(setDto.getCompleted())
                                .build();
                        workoutExercise.addSet(workoutSet);
                    }
                }
                session.addExercise(workoutExercise);
            }
        }

        session = workoutSessionRepository.save(session);
        return mapToDTO(session);
    }

    @Override
    public Page<WorkoutSessionDTO> getWorkoutHistory(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return workoutSessionRepository
                .findAllByUserOrderByDateDesc(getCurrentUser(), pageable)
                .map(this::mapToDTO);
    }

    private WorkoutSessionDTO mapToDTO(WorkoutSessionEntity entity) {
        List<ExerciseDTO> exercises = entity.getExercises().stream().map(we ->
            ExerciseDTO.builder()
                .id(we.getId())
                .name(we.getName())
                .muscleGroup(we.getMuscleGroup())
                .jointPain(we.getJointPain())
                .possibleInjury(we.getPossibleInjury())
                .feelingSick(we.getFeelingSick())
                .sets(we.getSets().stream().map(ws ->
                    SetDTO.builder()
                        .id(ws.getId())
                        .setType(ws.getSetType())
                        .targetRepRange(ws.getTargetRepRange())
                        .targetWeight(ws.getTargetWeight())
                        .targetTimeSeconds(ws.getTargetTimeSeconds())
                        .reps(ws.getReps())
                        .weight(ws.getWeight())
                        .completed(ws.getCompleted())
                        .build()
                ).collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList());

        return WorkoutSessionDTO.builder()
                .id(entity.getId())
                .routineName(entity.getRoutineName())
                .date(entity.getDate().toString())
                .durationMinutes(entity.getDurationMinutes())
                .totalVolume(entity.getTotalVolume())
                .completedSets(entity.getCompletedSets())
                .totalSets(entity.getTotalSets())
                .muscleGroupsTrained(new ArrayList<>(entity.getMuscleGroupsTrained()))
                .exercises(exercises)
                .build();
    }
}
