package com.example.eka.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(columnDefinition = "TEXT")
    private String response;

    private LocalDateTime createdAt;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public ChatMessage(){

    }
    public ChatMessage(String message,String response){
        this.message= message;
        this.response= response;
        this.createdAt=LocalDateTime.now();
    }
    public Long getId(){
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getResponse() {
        return response;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
