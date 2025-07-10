package com.example.jarvisbackend.services;

import com.example.jarvisbackend.models.Task;
import com.example.jarvisbackend.repository.TaskRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.openai.OpenAiChatModel;


@Service
public class JarvisAiService {

    private final OpenAiChatModel chatClient;
    private final PromptBuilderService promptBuilderService;
    private final TaskRepository taskRepository;

    @Autowired
    public JarvisAiService(OpenAiChatModel chatClient, PromptBuilderService promptBuilderService, TaskRepository taskRepository) {
        this.chatClient = chatClient;
        this.promptBuilderService = promptBuilderService;
        this.taskRepository = taskRepository;
    }

    public String askJarvis(String input) {
        String prompt = promptBuilderService.buildPrompt(input);
        ChatResponse response = chatClient.call(new Prompt(prompt));
        String aiResponse = response.getResult().getOutput().getText();

        if (isStructuredCommand(aiResponse)) {
            handleCommand(aiResponse);
            return "Action executed based on your request: " + aiResponse;
        }

        return aiResponse;
    }

    private boolean isStructuredCommand(String aiResponse) {
        return aiResponse.toLowerCase().startsWith("action:");
    }

    private void handleCommand(String aiResponse) {
        System.out.println("AI Command: " + aiResponse);

        String[] parts = aiResponse.split(",");
        String action = null;
        Long id = null;
        Boolean completed = null;
        String title = null;
        String description = null;
        String deadline = null;

        for (String part : parts) {
            String[] keyValue = part.trim().split(":", 2);
            if (keyValue.length != 2) continue;

            String key = keyValue[0].trim().toLowerCase();
            String value = keyValue[1].trim();

            switch (key) {
                case "action" -> action = value.toLowerCase();
                case "id" -> {
                    try {
                        id = Long.parseLong(value);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format: " + value);
                    }
                }
                case "completed" -> completed = Boolean.parseBoolean(value);
                case "title" -> title = value;
                case "description" -> description = value;
                case "deadline" -> deadline = value;
            }
        }

        if ("update".equals(action) && id != null && completed != null) {
            Task task = taskRepository.findById(id).orElse(null);
            if (task != null) {
                task.setCompleted(completed);
                taskRepository.save(task);
                System.out.println("Updated task ID " + id + " to completed=" + completed);
            } else {
                System.out.println("Task with ID " + id + " not found for update.");
            }

        } else if ("delete".equals(action) && id != null) {
            if (taskRepository.existsById(id)) {
                taskRepository.deleteById(id);
                System.out.println("Deleted task with ID " + id);
            } else {
                System.out.println("Task with ID " + id + " does not exist.");
            }

        } else if ("create".equals(action)) {
            Task task = new Task();
            task.setTitle(title != null ? title : "Untitled Task");
            task.setDescription(description != null ? description : "");
            task.setDeadline(deadline != null ? deadline : "");
            taskRepository.save(task);
            System.out.println("Created new task with title: " + task.getTitle());
        }
    }

}
