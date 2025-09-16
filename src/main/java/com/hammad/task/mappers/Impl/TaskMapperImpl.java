package com.hammad.task.mappers.Impl;

import com.hammad.task.domain.dtos.TaskDto;
import com.hammad.task.domain.entities.Task;
import com.hammad.task.mappers.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus()
        );
    }

    @Override
    public Task fromDto(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.id());
        task.setName(taskDto.name());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());
        return task;
    }
}
