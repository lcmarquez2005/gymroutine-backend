package org.example.gymroutine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routine_sets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineSetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_exercise_id", nullable = false)
    private RoutineExerciseEntity routineExercise;

    private Integer reps;

    private Float weight;
}
