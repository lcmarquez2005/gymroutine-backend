package org.example.gymroutine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_session_id", nullable = false)
    private WorkoutSessionEntity workoutSession;

    @Column(nullable = false)
    private String name;

    @Column(name = "muscle_group")
    private String muscleGroup;

    @Column(name = "joint_pain")
    @Builder.Default
    private Boolean jointPain = false;

    @Column(name = "possible_injury")
    @Builder.Default
    private Boolean possibleInjury = false;

    @Column(name = "feeling_sick")
    @Builder.Default
    private Boolean feelingSick = false;

    @OneToMany(mappedBy = "workoutExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkoutSetEntity> sets = new ArrayList<>();

    public void addSet(WorkoutSetEntity set) {
        sets.add(set);
        set.setWorkoutExercise(this);
    }
}
