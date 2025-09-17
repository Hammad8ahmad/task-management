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
                task.getTask_id(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

    @Override
    public Task fromDto(TaskDto taskDto) {
        Task task = new Task();
        task.setTask_id(taskDto.id());
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());
        return task;
    }
}
