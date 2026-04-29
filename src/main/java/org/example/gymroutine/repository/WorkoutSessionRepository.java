package org.example.gymroutine.repository;

import org.example.gymroutine.entity.WorkoutSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSessionEntity, String> {
    List<WorkoutSessionEntity> findAllByUserIdOrderByDateDesc(String userId);
}
