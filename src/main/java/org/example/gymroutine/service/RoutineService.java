package org.example.gymroutine.service;

import org.example.gymroutine.model.response.RoutineDTO;

import java.util.List;

public interface RoutineService {
    List<RoutineDTO> getAllRoutines();
    RoutineDTO getRoutineById(String id);
    RoutineDTO createRoutine(RoutineDTO request);
    RoutineDTO updateRoutine(String id, RoutineDTO request);
    void deleteRoutine(String id);
}
