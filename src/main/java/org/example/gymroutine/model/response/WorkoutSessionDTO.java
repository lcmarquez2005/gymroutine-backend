package org.example.gymroutine.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionDTO {
    private String id;
    private String routineName;
    private String date;
    private Integer durationMinutes;
    private Float totalVolume;
    private Integer completedSets;
    private Integer totalSets;
    @Builder.Default
    private List<String> muscleGroupsTrained = new ArrayList<>();
    @Builder.Default
    private List<ExerciseDTO> exercises = new ArrayList<>();
}
