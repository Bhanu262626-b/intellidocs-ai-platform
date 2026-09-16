package com.example.eka.document.service;


import com.example.eka.document.entity.DocumentChunk;
import com.example.eka.document.entity.DocumentEntity;
import com.example.eka.document.repository.DocumentChunkRepository;
import com.example.eka.document.repository.DocumentRepository;
import com.example.eka.embedding.service.EmbeddingService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Service
public class DocumentService {

    private final DocumentRepository repository;
    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository chunkRepository;

    public DocumentService(DocumentRepository repository, EmbeddingService embeddingService, DocumentChunkRepository chunkRepository){
        this.repository = repository;
        this.embeddingService = embeddingService;
        this.chunkRepository = chunkRepository;
    }

    public String extractText(MultipartFile file) throws IOException{

        PDDocument document =
                Loader.loadPDF(file.getBytes());

        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        List<String> chunks= chunkText(text);

        DocumentEntity documentEntity= new DocumentEntity(file.getOriginalFilename(), text);
        repository.save(documentEntity);

        for(String chunk:chunks){
            float[] embeddingArray = embeddingService.generateEmbedding(chunk);

            List<Float> embedding = new ArrayList<>(embeddingArray.length);
            for (float value : embeddingArray) {
                embedding.add(value);
            }
            DocumentChunk documentChunk =
                    new DocumentChunk(chunk, embedding.toString());

            documentChunk.setDocument(documentEntity);

            chunkRepository.save(documentChunk);

            System.out.println("Embedding size:"+embedding.size());

            /// DocumentChunk documentChunk = new DocumentChunk(chunk, embedding.toString());
            // chunkRepository.save(documentChunk);
        }

        System.out.println("Total chunks: "+chunks.size());

        for(String chunk:chunks) {
            System.out.println("Chunks:");
            System.out.println(chunk);
        }
        document.close();


        return text;

    }
    public List<DocumentEntity> getAllDocuments() {
        return repository.findAll();
    }

    public DocumentEntity getDocumentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Document not found"));
    }

    public void deleteDocument(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Document not found");
        }
        repository.deleteById(id);
    }

    private List<String> chunkText(String text){
        int chunkSize=1000;
        List<String> chunks =  new ArrayList<>();
        for(int i=0;i<text.length();i+=chunkSize){
            int end=Math.min(i+chunkSize,text.length());
            chunks.add(text.substring(i,end));
        }
       return chunks;
    }


}
