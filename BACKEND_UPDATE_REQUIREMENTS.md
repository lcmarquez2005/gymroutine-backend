# Requisitos de Actualización del Backend (GymRoutine)

Este documento sirve como contexto y guía para actualizar la base de datos y la API de Spring Boot. El objetivo es dar soporte oficial a las nuevas funciones del frontend (feedback de salud, rangos de reps de texto, y sets de tiempo) sin necesidad de usar trucos o "hacks" en el frontend.

## 1. Feedback de Salud (Entrenamiento Activo)

Actualmente, si un usuario marca que sintió dolor articular durante el `ActiveWorkout`, esa información se pierde al cerrar la app porque el backend no la recibe.

**Entidad a modificar:** `WorkoutExerciseEntity.java`
**Campos a agregar:**
```java
@Column(name = "joint_pain")
private Boolean jointPain = false;

@Column(name = "possible_injury")
private Boolean possibleInjury = false;

@Column(name = "feeling_sick")
private Boolean feelingSick = false;
```
*También deben agregarse a `ExerciseDTO`, `WorkoutSessionRequest` y su respectivo Mapper.*

## 2. Flexibilidad en Sets (Rutinas y Entrenamientos)

Para poder soportar que un set sea por "Tiempo" o por "Reps", y que el objetivo de reps sea un texto ("8-12") en lugar de un número estricto.

**Entidad a modificar:** `RoutineSetEntity.java` (Para guardar la meta de la rutina)
**Nuevos campos propuestos:**
```java
@Column(name = "set_type", length = 10)
private String setType; // Valores esperados: "REPS" o "TIME"

@Column(name = "target_rep_range", length = 20)
private String targetRepRange; // Ej: "8-12". Reemplaza al Integer 'reps'.

@Column(name = "target_weight")
private Double targetWeight; // Ej: 60.5 kg (Meta)

@Column(name = "target_time_seconds")
private Integer targetTimeSeconds; // Ej: 60 (para planchas)
```

**Entidad a modificar:** `WorkoutSetEntity.java` (Para guardar lo que realmente hiciste)
**Nuevos campos propuestos:**
```java
@Column(name = "set_type", length = 10)
private String setType; // "REPS" o "TIME"

// Los campos existentes 'reps' (Integer) y 'weight' (Double) se mantienen.
// Si es de tipo REPS, 'reps' guarda las repeticiones hechas.
// Si es de tipo TIME, 'reps' guardará los segundos realizados.
```
*Estos cambios también implican actualizar los DTOs de Request/Response para Rutinas y Entrenamientos.*

---
**Nota sobre la Ejecución:**
Dado que tienes el proyecto backend abierto (`WorkoutService.java`), podemos implementar estos cambios en los archivos de Java correspondientes ahora mismo si así lo deseas.
