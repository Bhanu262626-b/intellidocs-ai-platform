package com.example.eka.rag.dto;

public class RagRequest {
    private String question;
    private Long documentId;



    public String getQuestion() {
        return question;
    }

    public Long getDocumentId() {
            return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
