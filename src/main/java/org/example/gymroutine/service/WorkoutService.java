package org.example.gymroutine.service;

import org.example.gymroutine.model.response.WorkoutSessionDTO;
import org.springframework.data.domain.Page;

public interface WorkoutService {
    WorkoutSessionDTO createWorkoutSession(WorkoutSessionDTO request);
    Page<WorkoutSessionDTO> getWorkoutHistory(int page, int size);
}
