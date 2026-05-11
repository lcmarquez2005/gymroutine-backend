package org.example.gymroutine.repository;

import org.example.gymroutine.entity.UserEntity;
import org.example.gymroutine.entity.WorkoutSessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSessionEntity, String> {
    Page<WorkoutSessionEntity> findAllByUserOrderByDateDesc(UserEntity user, Pageable pageable);
}
