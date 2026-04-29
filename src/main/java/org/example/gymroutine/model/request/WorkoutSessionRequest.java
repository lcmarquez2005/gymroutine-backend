package org.example.gymroutine.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class WorkoutSessionRequest {

    @NotBlank(message = "Routine name is required")
    private String routineName;

    @NotNull(message = "Date is required")
    private Instant date;

    @Min(value = 0, message = "Duration cannot be negative")
    private Integer durationMinutes;

    @Min(value = 0, message = "Total volume cannot be negative")
    private Float totalVolume;

    @Min(value = 0, message = "Completed sets cannot be negative")
    private Integer completedSets;

    @Min(value = 0, message = "Total sets cannot be negative")
    private Integer totalSets;

    @NotNull(message = "Muscle groups trained list cannot be null")
    private List<String> muscleGroupsTrained;

    @NotNull(message = "Exercises list cannot be null")
    @Valid
    private List<WorkoutExerciseRequest> exercises;
}
