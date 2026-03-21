package com.nicolaszbq.ExerciseWorksheetManager.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
    public Client client = new Client();

    public String getGeminiResponse(String prompt){
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3-flash-preview",
                        prompt,
                        null);
        return response.text();
    }

}
