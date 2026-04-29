package org.example.gymroutine.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetDTO {
    private String id;
    private Integer reps;
    private Float weight;
    private Boolean completed;
}
