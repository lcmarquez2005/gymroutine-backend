package org.example.gymroutine.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.model.response.WorkoutSessionDTO;
import org.example.gymroutine.service.WorkoutService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<Page<WorkoutSessionDTO>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(workoutService.getWorkoutHistory(page, size));
    }

    @PostMapping
    public ResponseEntity<WorkoutSessionDTO> create(@RequestBody WorkoutSessionDTO request) {
        return ResponseEntity.ok(workoutService.createWorkoutSession(request));
    }
}
