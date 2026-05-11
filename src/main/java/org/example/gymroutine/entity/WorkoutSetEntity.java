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

    @Column(name = "set_type", length = 10)
    private String setType;

    @Column(name = "target_rep_range", length = 20)
    private String targetRepRange;

    @Column(name = "target_weight")
    private Float targetWeight;

    @Column(name = "target_time_seconds")
    private Integer targetTimeSeconds;

    private Integer reps;

    private Float weight;

    private Boolean completed;
}
