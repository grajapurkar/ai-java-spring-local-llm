package com.example.aipoc.controller;
import com.example.aipoc.model.ChatRequest;
import com.example.aipoc.model.ChatResponse;
import com.example.aipoc.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatResponse ask(
            @RequestBody ChatRequest request) {

        String response =
                chatService.ask(request.question());

        return new ChatResponse(response);
    }
}