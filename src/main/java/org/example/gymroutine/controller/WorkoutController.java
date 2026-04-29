package org.example.gymroutine.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gymroutine.model.request.WorkoutSessionRequest;
import org.example.gymroutine.model.response.WorkoutSessionResponse;
import org.example.gymroutine.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutSessionResponse> saveWorkout(@Valid @RequestBody WorkoutSessionRequest request) {
        WorkoutSessionResponse response = workoutService.saveWorkoutSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutSessionResponse>> getHistory() {
        List<WorkoutSessionResponse> history = workoutService.getWorkoutsByUser();
        return ResponseEntity.ok(history);
    }
}
