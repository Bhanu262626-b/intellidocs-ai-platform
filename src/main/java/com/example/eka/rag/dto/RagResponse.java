package com.example.eka.rag.dto;

public class RagResponse {

    private String answer;

    public RagResponse(){

    }
    public RagResponse(String answer){
        this.answer=answer;
    }
    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }
}
