package com.example.jarvisbackend.components;


import com.example.jarvisbackend.DTO.ParsedCommand;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TaskCommandParser {

    public Optional<ParsedCommand> parse(String input) {
        input = input.toLowerCase();

        if(input.matches(".*(mark|change).*(done|completed)")) {
            Long id = extractId(input);
            return Optional.of(new ParsedCommand("update", id, Map.of("status", "done")));
        }

        if (input.matches(".*delete.*task.*")) {
            if (input.matches(".*id.*\\d+.*")) {
                Long id = extractId(input);
                return Optional.of(new ParsedCommand("delete", id, Map.of()));
            } else {
                String title = extractQuotedTitle(input);
                return Optional.of(new ParsedCommand("deleteByTitle", null, Map.of("title", title)));
            }
        }

        if (input.matches(".*add.*task.*") || input.matches(".*create.*task.*")) {
            String title = extractQuotedTitle(input);
            return Optional.of(new ParsedCommand("create", null, Map.of("title", title)));
        }

        return Optional.empty();
    }

    private Long extractId(String input) {
        Matcher matcher = Pattern.compile("(task\\s*)?(id\\s*)?(\\d+)").matcher(input);
        return matcher.find() ? Long.parseLong(matcher.group(3)) : null;
    }

    private String extractQuotedTitle(String input) {
        Matcher matcher = Pattern.compile("'(.*?)'").matcher(input);
        return matcher.find() ? matcher.group(1) : null;
    }

}
