package org.example.gymroutine.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.entity.UserEntity;
import org.example.gymroutine.entity.WorkoutExerciseEntity;
import org.example.gymroutine.entity.WorkoutSessionEntity;
import org.example.gymroutine.entity.WorkoutSetEntity;
import org.example.gymroutine.model.request.WorkoutExerciseRequest;
import org.example.gymroutine.model.request.WorkoutSessionRequest;
import org.example.gymroutine.model.request.WorkoutSetRequest;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.example.gymroutine.model.response.SetDTO;
import org.example.gymroutine.model.response.WorkoutSessionResponse;
import org.example.gymroutine.repository.WorkoutSessionRepository;
import org.example.gymroutine.service.WorkoutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.example.gymroutine.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
//...

import org.example.gymroutine.mapper.WorkoutMapper;
//...

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserRepository userRepository;
    private final WorkoutMapper workoutMapper;

    private UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public WorkoutSessionResponse saveWorkoutSession(WorkoutSessionRequest request) {
        UserEntity user = getCurrentUser();
        
        // Calcular volumen real
        float calculatedVolume = 0f;
        int calculatedCompletedSets = 0;
        int calculatedTotalSets = 0;

        for (WorkoutExerciseRequest ex : request.getExercises()) {
            for (WorkoutSetRequest set : ex.getSets()) {
                calculatedTotalSets++;
                if (Boolean.TRUE.equals(set.getCompleted())) {
                    calculatedCompletedSets++;
                    calculatedVolume += (set.getWeight() * set.getReps());
                }
            }
        }

        WorkoutSessionEntity sessionEntity = WorkoutSessionEntity.builder()
                .user(user)
                .routineName(request.getRoutineName())
                .date(request.getDate())
                .durationMinutes(request.getDurationMinutes())
                .totalVolume(calculatedVolume) // Sobreescribimos con nuestro cálculo
                .completedSets(calculatedCompletedSets)
                .totalSets(calculatedTotalSets)
                .muscleGroupsTrained(new ArrayList<>(request.getMuscleGroupsTrained()))
                .build();

        for (WorkoutExerciseRequest exReq : request.getExercises()) {
            WorkoutExerciseEntity exerciseEntity = WorkoutExerciseEntity.builder()
                    .name(exReq.getName())
                    .muscleGroup(exReq.getMuscleGroup())
                    .build();

            for (WorkoutSetRequest setReq : exReq.getSets()) {
                WorkoutSetEntity setEntity = WorkoutSetEntity.builder()
                        .reps(setReq.getReps())
                        .weight(setReq.getWeight())
                        .completed(setReq.getCompleted())
                        .build();
                exerciseEntity.addSet(setEntity);
            }
            sessionEntity.addExercise(exerciseEntity);
        }

        WorkoutSessionEntity savedEntity = workoutSessionRepository.save(sessionEntity);
        return mapToResponse(savedEntity);
    }

    @Override
    public List<WorkoutSessionResponse> getWorkoutsByUser() {
        return workoutSessionRepository.findAllByUserIdOrderByDateDesc(getCurrentUser().getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private WorkoutSessionResponse mapToResponse(WorkoutSessionEntity entity) {
        return workoutMapper.toResponse(entity);
    }
}
