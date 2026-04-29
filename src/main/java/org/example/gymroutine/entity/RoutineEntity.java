package org.example.gymroutine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "target_muscle_group")
    private String targetMuscleGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ElementCollection
    @CollectionTable(name = "routine_assigned_days", joinColumns = @JoinColumn(name = "routine_id"))
    @Column(name = "day_of_week")
    @Builder.Default
    private List<String> assignedDays = new ArrayList<>();

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RoutineExerciseEntity> exercises = new ArrayList<>();

    public void addExercise(RoutineExerciseEntity exercise) {
        exercises.add(exercise);
        exercise.setRoutine(this);
    }
}
