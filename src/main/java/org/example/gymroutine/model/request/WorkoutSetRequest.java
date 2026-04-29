package org.example.gymroutine.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkoutSetRequest {

    @NotNull(message = "Reps is required")
    @Min(value = 0, message = "Reps cannot be negative")
    private Integer reps;

    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight cannot be negative")
    private Float weight;

    @NotNull(message = "Completed status is required")
    private Boolean completed;
}
