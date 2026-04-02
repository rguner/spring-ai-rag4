package com.example.rag.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping(path = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatResponse chat(@RequestBody ChatRequest request) {
        if (request.message() == null || request.message().isBlank()) {
            return new ChatResponse("Lütfen boş olmayan bir mesaj gönderin.");
        }
        String reply = chatClient.prompt()
                .user(request.message().trim())
                .call()
                .content();
        return new ChatResponse(reply);
    }

    @GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public String health() {
        String timestamp = java.time.OffsetDateTime.now().toString();
        return String.format("{\"status\":\"OK\", \"timestamp\":\"%s\"}", timestamp);
    }

}
