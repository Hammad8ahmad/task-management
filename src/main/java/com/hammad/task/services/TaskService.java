package com.hammad.task.services;



import com.hammad.task.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<Task> listOfTasks();
    Task createTask(Task task);
    void deleteTask(UUID task_id);
    Task updateTask(UUID task_id, Task task);
}
