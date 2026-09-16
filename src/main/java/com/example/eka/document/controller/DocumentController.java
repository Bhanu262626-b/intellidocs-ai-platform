package com.example.eka.document.controller;

import com.example.eka.document.entity.DocumentEntity;
import com.example.eka.document.service.DocumentService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService){
        this.documentService=documentService;
    }

    @PostMapping(value="/upload",
            consumes= MediaType.MULTIPART_FORM_DATA_VALUE)

    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        return documentService.extractText(file);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public List<DocumentEntity> getDocuments(){
        return documentService.getAllDocuments();
    }
    @GetMapping("/{id}")
    public DocumentEntity getDocument(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }
}
