package com.hammad.task.services.Impl;

import com.hammad.task.domain.entities.Task;
import com.hammad.task.domain.entities.User;
import com.hammad.task.repositories.TaskRepository;
import com.hammad.task.repositories.UserRepository;
import com.hammad.task.services.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in database"));
    }


    @Override
    public List<Task> listOfTasks() {

        User currentUser = getCurrentUser();
        return taskRepository.findByUser(currentUser);

    }

    @Override
    public Task createTask(Task task) {
        if (task.getTask_id() != null) {
            throw new IllegalArgumentException("Task ID must be null for new tasks");
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        if (task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }

        Task newTask = new Task();
        newTask.setTitle(task.getTitle().trim());
        newTask.setDescription(task.getDescription().trim());
        newTask.setStatus(task.getStatus());
        newTask.setUser(getCurrentUser());

        return taskRepository.save(newTask);
    }

    @Override
    public void deleteTask(UUID task_id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + task_id));

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("You are not authorized to delete this task");
        }

        taskRepository.deleteById(task_id);
    }

    @Override
    public Task updateTask(UUID task_id, Task task) {
        User currentUser = getCurrentUser();
        Task existingTask = taskRepository.findById(task_id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + task_id));

        if (!existingTask.getUser().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("You are not authorized to update this task");
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        if (task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }

        existingTask.setTitle(task.getTitle().trim());
        existingTask.setDescription(task.getDescription().trim());
        existingTask.setStatus(task.getStatus());

        return taskRepository.save(existingTask);
    }

}
