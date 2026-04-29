package org.example.gymroutine.mapper;

import org.example.gymroutine.entity.RoutineEntity;
import org.example.gymroutine.entity.RoutineExerciseEntity;
import org.example.gymroutine.entity.RoutineSetEntity;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.example.gymroutine.model.response.RoutineDTO;
import org.example.gymroutine.model.response.SetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoutineMapper {

    RoutineDTO toDto(RoutineEntity entity);

    @Mapping(target = "id", source = "exercise.id")
    @Mapping(target = "name", source = "exercise.name")
    @Mapping(target = "muscleGroup", source = "exercise.muscleGroup")
    ExerciseDTO toExerciseDto(RoutineExerciseEntity entity);

    SetDTO toSetDto(RoutineSetEntity entity);
}
