package org.example.gymroutine.service;

import org.example.gymroutine.entity.UserEntity;
import org.example.gymroutine.model.request.WorkoutSessionRequest;
import org.example.gymroutine.model.response.WorkoutSessionResponse;

import java.util.List;

public interface WorkoutService {
    WorkoutSessionResponse saveWorkoutSession(WorkoutSessionRequest request);
    List<WorkoutSessionResponse> getWorkoutsByUser();
}
