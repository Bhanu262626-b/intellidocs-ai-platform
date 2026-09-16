package com.example.eka.document.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    @Column(columnDefinition="Text")

    private String content;

    private LocalDateTime uploadedAt;

    @Column(unique = true, length = 64)
    private String fileHash;

    @JsonIgnore
    @OneToMany(
            mappedBy = "document",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DocumentChunk> chunks = new ArrayList<>();

    public List<DocumentChunk> getChunks() {
        return chunks;
    }

    public void addChunk(DocumentChunk chunk) {
        chunks.add(chunk);
        chunk.setDocument(this);
    }

    public void removeChunk(DocumentChunk chunk) {
        chunks.remove(chunk);
        chunk.setDocument(null);
    }

    public DocumentEntity(){

    }
    public DocumentEntity(String fileName,String content){
        this.fileName=fileName;
        this.content=content;
        this.uploadedAt=LocalDateTime.now();
    }
    public Long getId(){
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

}
