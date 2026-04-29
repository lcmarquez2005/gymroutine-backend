package org.example.gymroutine.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Collections;

@Data
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
    @Builder.Default
    private List<Object> trainingDays = Collections.emptyList();
}
