package com.example.jarvisbackend.services;

import com.example.jarvisbackend.models.Task;
import com.example.jarvisbackend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptBuilderService {

    private final TaskRepository taskRepository;

    public PromptBuilderService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public String buildPrompt(String userQuestion) {
        if (isAboutTasks(userQuestion)) {
            List<Task> tasks = taskRepository.findAll();
            String taskContext = formatTasks(tasks);

            return """
                You are Jarvis, an AI assistant that manages tasks. The user will ask you to create, update, or delete tasks.
            
                Your response must always be in this exact structured format:
                action:create, title:<title>, description:<description>, deadline:<deadline>
                OR
                action:update, id:<id>, completed:<true/false>
                OR
                action:delete, id:<id>
            
                Here is the current list of tasks:
                %s
            
                USER QUESTION:
                %s
                """.formatted(taskContext, userQuestion);
        }

        // For general questions unrelated to task manipulation
        return "You are Jarvis, a helpful AI assistant. Answer the following:\n\n" + userQuestion;
    }

    private boolean isAboutTasks(String question) {
        String q = question.toLowerCase();
        return q.contains("task") || q.contains("todo") || q.contains("deadline");
    }

    private String formatTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No tasks found.";
        }

        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append("ID: ").append(task.getId()).append("\n")
                    .append("Title: ").append(task.getTitle()).append("\n")
                    .append("Description: ").append(task.getDescription()).append("\n")
                    .append("Deadline: ").append(task.getDeadline()).append("\n")
                    .append("Completed: ").append(task.isCompleted()).append("\n\n");
        }
        return sb.toString();
    }
}
