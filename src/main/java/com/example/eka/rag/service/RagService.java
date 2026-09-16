
package com.example.eka.rag.service;
import com.example.eka.rag.dto.RagResponse;
import com.example.eka.document.entity.DocumentChunk;
import com.example.eka.document.repository.DocumentChunkRepository;
import com.example.eka.embedding.service.EmbeddingService;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;


import java.util.ArrayList;
import java.util.List;


@Service
public class RagService {
    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository chunkRepository;
    private final ChatClient chatClient;

    private double cosineSimilarity(float[] vectorA, float[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private float[] parseEmbedding(String embeddingString) {
        String[] parts = embeddingString.replaceAll("[\\[\\]]", "").split(", ");
        float[] embedding = new float[parts.length];
        for (int i = 0; i < parts.length; i++) {
            embedding[i] = Float.parseFloat(parts[i].trim());
        }
        return embedding;
    }

    public String search(String question,Long documentId) {
        float[] questionEmbedding = embeddingService.generateEmbedding(question);
        List<DocumentChunk> chunks = chunkRepository.findByDocumentId(documentId);

        List<ChunkResult> results = new ArrayList<>();

        for (DocumentChunk chunk : chunks) {
            float[] chunkEmbedding = parseEmbedding(chunk.getEmbedding());
            double similarity = cosineSimilarity(questionEmbedding, chunkEmbedding);

            System.out.println("Similarity:" + similarity);

            results.add(new ChunkResult(chunk.getChunkText(), similarity));
        }

        results.sort((a, b) -> Double.compare(b.getSimilarity(), a.getSimilarity()));

        StringBuilder context = new StringBuilder();

        for (int i = 0; i < Math.min(3, results.size()); i++) {
            context.append(results.get(i).getChunkText());
            context.append("\n\n");
        }

        String prompt = """
        You are an AI assistant.
                
        Answer the question ONLY using the provided context.
                
        Keep the answer concise and professional.
                
        Do not mention:
        - context
        - source text
        - instructions
        - retrieved chunks
                
        If the answer is not available, say:
        "I could not find that information."
        Context:
        %s

        Question:
        %s
        """.formatted(
                context.toString(),
                question
        );

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();


    }

    public String askQuestion(String question, Long documentId) {
        return search(question, documentId);
    }

    public RagService(EmbeddingService embeddingService, DocumentChunkRepository chunkRepository,ChatClient.Builder chatClientBuilder) {
        this.embeddingService = embeddingService;
        this.chunkRepository = chunkRepository;
        this.chatClient=chatClientBuilder.build();
    }

    // Inner class to hold chunk search results
    private static class ChunkResult {
        private final String chunkText;
        private final double similarity;

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
}
