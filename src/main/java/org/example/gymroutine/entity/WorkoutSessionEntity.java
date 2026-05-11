package org.example.gymroutine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "routine_name", nullable = false)
    private String routineName;

    @Column(nullable = false)
    private Instant date;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "total_volume")
    private Float totalVolume;

    @Column(name = "completed_sets")
    private Integer completedSets;

    @Column(name = "total_sets")
    private Integer totalSets;

    @ElementCollection
    @CollectionTable(name = "workout_muscle_groups", joinColumns = @JoinColumn(name = "workout_session_id"))
    @Column(name = "muscle_group")
    @Builder.Default
    private List<String> muscleGroupsTrained = new ArrayList<>();

    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkoutExerciseEntity> exercises = new ArrayList<>();

    public void addExercise(WorkoutExerciseEntity exercise) {
        exercises.add(exercise);
        exercise.setWorkoutSession(this);
    }
}
