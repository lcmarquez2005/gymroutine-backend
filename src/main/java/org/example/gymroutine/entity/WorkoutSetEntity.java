package org.example.gymroutine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workout_sets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExerciseEntity workoutExercise;

    @Column(nullable = false)
    private Integer reps;

    @Column(nullable = false)
    private Float weight;

    @Column(nullable = false)
    private Boolean completed;
}
