package com.example.jarvisbackend.services;


import com.example.jarvisbackend.models.Task;
import com.example.jarvisbackend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    private final TaskRepository taskRepo;

    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task addTask(Task task) {
        return taskRepo.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepo.findById(id).orElseThrow();
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDeadline(updatedTask.getDeadline());
        task.setCompleted(updatedTask.isCompleted());
        return taskRepo.save(task);
    }

    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }

    //Methods for AI interaction with DB

    public String formatAllTasks() {
        List<Task> tasks = taskRepo.findAll();
        if (tasks.isEmpty()) return "You have no tasks.";

        StringBuilder sb = new StringBuilder("Here are your tasks:\n\n");
        for (Task task : tasks) {
            sb.append("ID: ").append(task.getId())
                    .append(" | Title: ").append(task.getTitle())
                    .append(" | Completed: ").append(task.isCompleted())
                    .append(" | Deadline: ").append(task.getDeadline())
                    .append("\n");
        }
        return sb.toString();
    }

    public String updateTaskStatusById(Long id, boolean completed) {
        Optional<Task> opt = taskRepo.findById(id);
        if (opt.isEmpty()) return "Task with ID " + id + " not found.";
        Task task = opt.get();
        task.setCompleted(completed);
        taskRepo.save(task);
        return "Task " + id + " marked as " + (completed ? "completed." : "not completed.");
    }

    public String deleteTaskByTitle(String title) {
        Optional<Task> taskOpt = taskRepo.findAll().stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(title))
                .findFirst();

        if (taskOpt.isEmpty()) return "No task titled '" + title + "' found.";

        taskRepo.delete(taskOpt.get());
        return "Task titled '" + title + "' deleted.";
    }

    public String createTaskFromProperties(Map<String, String> props) {
        String title = props.get("title");
        String description = props.getOrDefault("description", "");
        String deadline = props.getOrDefault("deadline", "");

        if (title == null || title.isBlank()) return "Cannot create a task without a title.";

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setCompleted(false);
        taskRepo.save(task);
        return "Task '" + title + "' created.";
    }
}
