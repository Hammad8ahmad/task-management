package com.hammad.task.domain.dtos;

import com.hammad.task.domain.entities.Status;

import java.util.UUID;

public record TaskDto(
        UUID id,
        String name,
        String description,
        Status status

) {
}
