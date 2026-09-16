package com.example.eka.chat.service;


import com.example.eka.chat.dto.ChatResponse;
import com.example.eka.chat.entity.ChatMessage;
import com.example.eka.chat.entity.Conversation;
import com.example.eka.chat.repository.ChatMessageRepository;
import com.example.eka.chat.repository.ConversationRepository;
import com.example.eka.rag.service.RagService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ChatService {


    private final ChatMessageRepository chatMessageRepository;
    private final ConversationRepository conversationRepository;
    private final RagService ragService;

    public class ChatResult {

        private Long conversationId;
        private String response;

        public ChatResult(Long conversationId, String response) {
            this.conversationId = conversationId;
            this.response = response;
        }

        public Long getConversationId() {
            return conversationId;
        }

        public String getResponse() {
            return response;
        }
    }

    public ChatService(ChatMessageRepository chatMessageRepository,ConversationRepository conversationRepository,RagService ragService
    ){

        this.chatMessageRepository= chatMessageRepository;
        this.conversationRepository=conversationRepository;
        this.ragService = ragService;

    }



    public ChatResponse askAi(Long conversationId,Long documentId,String message) {

        String response = ragService.askQuestion(
                message,
                documentId
        );
        Conversation conversation;
        if (conversationId == null) {

            conversation = new Conversation();

            conversation.setCreatedAt(LocalDateTime.now());

            String title = message.length() > 40
                    ? message.substring(0, 40) + "..."
                    : message;

            conversation.setTitle(title);

            conversationRepository.save(conversation);

        } else {

            conversation = conversationRepository.findById(conversationId)
                    .orElseThrow(() ->
                            new RuntimeException("Conversation not found"));

        }
        ChatMessage chatMessage = new ChatMessage(message, response);

        chatMessage.setConversation(conversation);

        chatMessageRepository.save(chatMessage);

        return new ChatResponse(
                conversation.getId(),
                response
        );

    }
    public Conversation createConversation(String title) {

        Conversation conversation = new Conversation();

        conversation.setTitle(title);

        conversation.setCreatedAt(LocalDateTime.now());

        return conversationRepository.save(conversation);
    }
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }


    public Conversation getConversation(Long id) {

        return conversationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Conversation not found"));
    }

    public List<ChatMessage> getHistory() {
        return chatMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<ChatMessage> getConversationMessages(Long conversationId) {

        return chatMessageRepository
                .findByConversationIdOrderByCreatedAtAsc(conversationId);

    }


}
