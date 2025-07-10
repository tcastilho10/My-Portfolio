package com.example.jarvisbackend.DTO;

import java.util.Map;

public record ParsedCommand(String action, Long taskId, Map<String, String> properties) {}
