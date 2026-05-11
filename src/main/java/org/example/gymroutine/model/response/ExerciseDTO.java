package org.example.gymroutine.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private String id;
    private String name;
    private String muscleGroup;
    private Boolean jointPain;
    private Boolean possibleInjury;
    private Boolean feelingSick;
    private Integer restTime;
    private List<SetDTO> sets;
}
