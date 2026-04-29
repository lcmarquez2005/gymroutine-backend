package org.example.gymroutine.repository;

import org.example.gymroutine.entity.RoutineEntity;
import org.example.gymroutine.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<RoutineEntity, String> {
    List<RoutineEntity> findAllByUser(UserEntity user);
}
