package com.example.eka.rag.service;



public class ChunkResult {


    private String chunkText;
    private double similarity;

    public ChunkResult(String chunkText, double similarity) {
        this.chunkText = chunkText;
        this.similarity = similarity;
    }

    public String getChunkText() {
        return chunkText;
    }
    public double getSimilarity() {
        return similarity;
    }
}