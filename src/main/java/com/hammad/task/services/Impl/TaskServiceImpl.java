package com.hammad.task.services.Impl;

import com.hammad.task.domain.entities.Task;
import com.hammad.task.domain.entities.User;
import com.hammad.task.repositories.TaskRepository;
import com.hammad.task.repositories.UserRepository;
import com.hammad.task.services.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    public List<Task> listOfTasks() {

        User currentUser = getCurrentUser();
        return taskRepository.findByUser(currentUser);

    }

    @Override
    public Task createTask(Task task) {
        if (task.getTask_id() != null) {
            throw new IllegalArgumentException("Task already has an id!");
        }
        if (task.getTitle() == null || task.getTitle().isBlank() ||
                task.getDescription() == null || task.getDescription().isBlank()) {
            throw new IllegalArgumentException("Task must contain a title and description!");
        }

        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());
        newTask.setUser(getCurrentUser());

        return taskRepository.save(newTask);
    }

    @Override
    public void deleteTask(UUID task_id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to delete this task");
        }

        taskRepository.deleteById(task_id);
    }

    @Override
    public Task updateTask(UUID task_id, Task task) {
        User currentUser = getCurrentUser();
        Task existingTask = taskRepository.findById(task_id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!existingTask.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to update this task");
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());

        return taskRepository.save(existingTask);
    }

}
