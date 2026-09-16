package com.example.eka.chat.dto;

public class ChatResponse {

    private Long conversationID;

    private String response;

    public ChatResponse(Long conversationID,String response){
        this.conversationID=conversationID;
        this.response = response;
    }

    public Long getConversationID() {
        return conversationID;
    }

    public String getResponse(){
        return response;
    }

}
