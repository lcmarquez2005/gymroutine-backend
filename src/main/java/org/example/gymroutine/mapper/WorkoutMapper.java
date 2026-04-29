package org.example.gymroutine.mapper;

import org.example.gymroutine.entity.WorkoutExerciseEntity;
import org.example.gymroutine.entity.WorkoutSessionEntity;
import org.example.gymroutine.entity.WorkoutSetEntity;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.example.gymroutine.model.response.SetDTO;
import org.example.gymroutine.model.response.WorkoutSessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkoutMapper {

    WorkoutSessionResponse toResponse(WorkoutSessionEntity entity);

    ExerciseDTO toExerciseDto(WorkoutExerciseEntity entity);

    SetDTO toSetDto(WorkoutSetEntity entity);
}
