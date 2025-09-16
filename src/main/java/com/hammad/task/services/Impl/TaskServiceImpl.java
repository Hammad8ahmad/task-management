package com.hammad.task.services.Impl;

import com.hammad.task.domain.entities.Task;
import com.hammad.task.repositories.TaskRepository;
import com.hammad.task.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listOfTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task createTask(Task task) {
        if (task.getTask_id() != null) {
            throw new IllegalArgumentException("Task already has an id!");
        }
        if (task.getName() == null || task.getName().isBlank() ||
                task.getDescription() == null || task.getDescription().isBlank()) {
            throw new IllegalArgumentException("Task must contain a name and description!");
        }

        Task newTask = new Task();
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());

        return taskRepository.save(newTask);
    }

    @Override
    public void deleteTask(UUID task_id) {
        if (!taskRepository.existsById(task_id)) {
            throw new IllegalArgumentException("Task not found");
        }
        taskRepository.deleteById(task_id);
    }

    @Override
    public Task updateTask(UUID task_id, Task task) {
        Task existingTask = taskRepository.findById(task_id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());

        return taskRepository.save(existingTask);
    }
}
