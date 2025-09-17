package com.hammad.task.domain.dtos;

import com.hammad.task.domain.entities.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TaskDto(
        UUID id,
        @NotBlank(message = "Task title is required")
        @Size(min = 1, max = 100, message = "Task title must be between 1 and 100 characters")
        String title,
        @NotBlank(message = "Task description is required")
        @Size(min = 1, max = 500, message = "Task description must be between 1 and 500 characters")
        String description,
        @NotNull(message = "Task status is required")
        Status status

) {
}
