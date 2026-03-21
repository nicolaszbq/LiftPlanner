package com.nicolaszbq.ExerciseWorksheetManager.controller;

import com.nicolaszbq.ExerciseWorksheetManager.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geminiapi")
public class GeminiController {
    @Autowired
    private GeminiService geminiService;

    @GetMapping("/response")
    public String getGeminiResponse(@RequestBody String prompt){
        return geminiService.getGeminiResponse(prompt);
    }
}
