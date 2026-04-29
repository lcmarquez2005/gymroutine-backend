package org.example.gymroutine.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymroutine.service.RoutineService;
import org.example.gymroutine.model.response.RoutineDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    @GetMapping
    public ResponseEntity<List<RoutineDTO>> getAll() {
        return ResponseEntity.ok(routineService.getAllRoutines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoutineDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(routineService.getRoutineById(id));
    }

    @PostMapping
    public ResponseEntity<RoutineDTO> create(@RequestBody RoutineDTO request) {
        return ResponseEntity.ok(routineService.createRoutine(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoutineDTO> update(@PathVariable String id, @RequestBody RoutineDTO request) {
        return ResponseEntity.ok(routineService.updateRoutine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.noContent().build();
    }
}
