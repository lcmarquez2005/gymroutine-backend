package org.example.gymroutine.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionResponse {
    private String id;
    private String routineName;
    private Instant date;
    private Integer durationMinutes;
    private Float totalVolume;
    private Integer completedSets;
    private Integer totalSets;
    private List<String> muscleGroupsTrained;
    private List<ExerciseDTO> exercises;
}
