package com.hammad.task.controllers;

import com.hammad.task.domain.dtos.TaskDto;
import com.hammad.task.domain.entities.Task;
import com.hammad.task.mappers.TaskMapper;
import com.hammad.task.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskMapper taskMapper;
    private final TaskService taskService;

    public TaskController(TaskMapper taskMapper, TaskService taskService){
        this.taskMapper = taskMapper;
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> listOfTasks() {
        return taskService.listOfTasks()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto){
        Task createdTask = taskService.createTask(taskMapper.fromDto(taskDto));
        return taskMapper.toDto(createdTask);
    }

    @DeleteMapping("/{task_id}")
    public void deleteTask(@PathVariable("task_id") UUID task_id){
        taskService.deleteTask(task_id);
    }

    @PutMapping("/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_id") UUID task_id,
            @RequestBody TaskDto taskDto
    ){
        Task updatedTask = taskService.updateTask(task_id, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updatedTask);
    }
}
