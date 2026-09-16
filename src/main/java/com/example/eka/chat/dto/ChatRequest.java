package com.example.eka.chat.dto;

public class ChatRequest {

    private Long conversationId;
    private Long documentId;
    private String message;

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getDocumentId(){
        return documentId;
    }

    public void setDocumentId(Long documentId){
        this.documentId=documentId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
