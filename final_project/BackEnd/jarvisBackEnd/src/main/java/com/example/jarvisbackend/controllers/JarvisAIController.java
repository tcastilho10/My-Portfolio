package com.example.jarvisbackend.controllers;


import com.example.jarvisbackend.services.JarvisAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ai")
@CrossOrigin
public class JarvisAIController {

    @Autowired
    private JarvisAiService aiService;

    @PostMapping("/ask")
    public String askJarvis(@RequestBody String input) {
        return aiService.askJarvis(input);
    }

}
