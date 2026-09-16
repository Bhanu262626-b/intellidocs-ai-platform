package com.example.eka.chat.controller;

import com.example.eka.chat.entity.ChatMessage;
import com.example.eka.chat.entity.Conversation;
import com.example.eka.chat.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/conversations")
public class ConversationController {

    private final ChatService chatService;

    public ConversationController(ChatService chatService){
        this.chatService=chatService;
    }
    @PostMapping
    public Conversation createConversation(
            @RequestParam String title) {

        return chatService.createConversation(title);
    }

    @GetMapping
    public List<Conversation> getAllConversations() {
        return chatService.getAllConversations();
    }

    @GetMapping("/{id}/messages")
    public List<ChatMessage> getMessages(
            @PathVariable Long id) {

        return chatService.getConversationMessages(id);

    }

    @GetMapping("/{id}")
    public Conversation getConversation(
            @PathVariable Long id) {

        return chatService.getConversation(id);
    }
}
