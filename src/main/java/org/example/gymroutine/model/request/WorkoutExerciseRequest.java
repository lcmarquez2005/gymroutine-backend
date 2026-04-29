package org.example.gymroutine.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class WorkoutExerciseRequest {

    @NotBlank(message = "Exercise name is required")
    private String name;

    private String muscleGroup;

    @NotNull(message = "Sets list cannot be null")
    @Valid
    private List<WorkoutSetRequest> sets;
}
