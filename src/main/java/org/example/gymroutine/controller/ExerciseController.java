package org.example.gymroutine.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.service.ExerciseService;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAll() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> create(@RequestBody ExerciseDTO request) {
        return ResponseEntity.ok(exerciseService.createExercise(request));
    }
}
