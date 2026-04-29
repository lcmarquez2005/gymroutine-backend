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
public class RoutineDTO {
    private String id;
    private String name;
    private String targetMuscleGroup;
    @Builder.Default
    private List<String> assignedDays = new ArrayList<>();
    @Builder.Default
    private List<ExerciseDTO> exercises = new ArrayList<>();
    private Boolean isFavorite;
}
