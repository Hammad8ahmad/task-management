package com.hammad.task.domain.dtos;

public record ErrorResponse(
        int status,
        String message
) {
}

