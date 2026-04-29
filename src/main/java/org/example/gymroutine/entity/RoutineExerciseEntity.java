package org.example.gymroutine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routine_exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private RoutineEntity routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseEntity exercise;

    @Column(name = "rest_time")
    private Integer restTime;

    @Column(name = "list_order")
    private Integer listOrder;

    @OneToMany(mappedBy = "routineExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RoutineSetEntity> sets = new ArrayList<>();

    public void addSet(RoutineSetEntity set) {
        sets.add(set);
        set.setRoutineExercise(this);
    }
}
