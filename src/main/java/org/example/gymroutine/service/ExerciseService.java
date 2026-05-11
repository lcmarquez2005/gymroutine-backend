package org.example.gymroutine.service;

import org.example.gymroutine.model.response.ExerciseDTO;
import java.util.List;

public interface ExerciseService {
    List<ExerciseDTO> getAllExercises();
    ExerciseDTO createExercise(ExerciseDTO request);
    void deleteExercise(String id);
}
