package com.hammad.task.mappers;

import com.hammad.task.domain.dtos.TaskDto;
import com.hammad.task.domain.entities.Task;

public interface TaskMapper {

    TaskDto toDto(Task task);
    Task fromDto(TaskDto taskDto);
}
