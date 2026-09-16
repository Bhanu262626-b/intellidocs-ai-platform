package com.example.eka.chat.controller;

import com.example.eka.chat.entity.ChatMessage;
import java.util.List;
import com.example.eka.chat.dto.ChatRequest;
import com.example.eka.chat.dto.ChatResponse;
import com.example.eka.chat.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request){

        System.out.println("Conversation: " + request.getConversationId());
        System.out.println("Document: " + request.getDocumentId());
        System.out.println("Message: " + request.getMessage());

        return chatService.askAi(
                request.getConversationId(),
                request.getDocumentId(),
                request.getMessage()
        );
    }
    @GetMapping("/history")
    public List<ChatMessage> history() {
        return chatService.getHistory();
    }

}
