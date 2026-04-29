package org.example.gymroutine.mapper;

import org.example.gymroutine.entity.ExerciseEntity;
import org.example.gymroutine.model.response.ExerciseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseMapper {

    ExerciseDTO toDto(ExerciseEntity entity);
    
    ExerciseEntity toEntity(ExerciseDTO dto);
    
    List<ExerciseDTO> toDtoList(List<ExerciseEntity> entities);
}
