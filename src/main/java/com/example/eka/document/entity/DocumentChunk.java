package com.example.eka.document.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class DocumentChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(columnDefinition="Text")
    private String chunkText;

    @Setter
    @Column(columnDefinition="Text")
    private String embedding;

    public DocumentChunk() {
    }

    public DocumentChunk(String chunkText) {
        this.chunkText = chunkText;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private DocumentEntity document;

    public DocumentChunk(String chunkText, String embedding) {
        this.chunkText = chunkText;
        this.embedding = embedding;
    }

}
