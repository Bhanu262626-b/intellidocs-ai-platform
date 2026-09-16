package com.example.eka.rag.controller;

import com.example.eka.rag.dto.RagRequest;
import com.example.eka.rag.dto.RagResponse;
import com.example.eka.rag.service.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rag")
public class RagController {
    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/text")
    public String testRag() {
        //ragService.search("What is Spring AI?");

        return "RAG search completed";
    }

    @PostMapping("/ask")
    public RagResponse askQuestion(
            @RequestBody RagRequest request) {

        String answer=ragService.askQuestion(
                request.getQuestion(),
                request.getDocumentId()
        );
        return new RagResponse(answer);
    }
}
